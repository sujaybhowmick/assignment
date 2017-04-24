package com.sentifi.assignment.services;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.sentifi.assignment.domain.Ticker;
import com.sentifi.assignment.domain.TickerData;
import com.sentifi.assignment.exceptions.DataAccessException;
import com.sentifi.assignment.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sujay on 4/11/17.
 */
@Service
public class TickerServiceImpl extends CachedTickerService {

    private static final int NUMBER_OF_DAYS = -200;
    private static final int MAX_NUMBER_OF_SYMBOLS = 1000;
    private static final int API_CACHE_SIZE = 1000;

    private com.sentifi.assignment.repository.Repository repository;

    @Autowired
    public TickerServiceImpl(@Qualifier("QuandlRemoteRepository")com.sentifi.assignment.repository.Repository repository){
        super(API_CACHE_SIZE);
        this.repository = repository;
    }

    @Override
    public String closePrice(String symbol, String startDate, String endDate) {

        try {
            LocalDate sDate = LocalDate.parse(startDate);
            LocalDate eDate = LocalDate.parse(endDate);

            Ticker ticker = this.lruCache.get(symbol);
            if (ticker == null) {
                ticker = this.repository.findBySymbol(symbol);
                this.lruCache.set(symbol, ticker);
            }
            List<TickerData> tickerDataList = ticker.getTickerData().stream()
                    .filter(t -> t.getDate().compareTo(eDate) >= 0 && t.getDate().compareTo(sDate) <= 0)
                    .collect(Collectors.toList());

            return getClosePriceJSONResponse(symbol, tickerDataList);
        }catch (Exception e){
            throw new ResourceNotFoundException(e);
        }

    }

    private String getClosePriceJSONResponse(String symbol, List<TickerData> tickerDataList){
        JsonFactory jsonFactory = new JsonFactory();
        StringWriter stringWriter = new StringWriter();
        try(JsonGenerator jsonGenerator = jsonFactory.createGenerator(stringWriter)) {

            jsonGenerator.writeStartObject();
            jsonGenerator.writeArrayFieldStart("Prices");

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("Ticker", symbol);

            for(TickerData tickerData: tickerDataList) {
                jsonGenerator.writeArrayFieldStart("DateClose");
                jsonGenerator.writeString(String.valueOf(tickerData.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                jsonGenerator.writeString(String.valueOf(tickerData.getClose()));
                jsonGenerator.writeEndArray();
            }
            jsonGenerator.writeEndObject();
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
            jsonGenerator.close();
            return stringWriter.toString();
        }catch(IOException e){
            throw new DataAccessException(e);
        }
    }

    public String dma200(String symbol, String startDate) {

        LocalDate sDate = LocalDate.parse(startDate);
        Ticker ticker = this.lruCache.get(symbol);
        if(ticker == null){
            ticker = this.repository.findBySymbol(symbol);
            this.lruCache.set(symbol, ticker);

        }
        Double dma = calculateDma200(sDate, sDate.plusDays(NUMBER_OF_DAYS), ticker);

        return getDma200JSONResponse(symbol, dma);
    }

    @Override
    public String dma200Bulk(String[] symbols, String startDate) {
        LocalDate sDate = LocalDate.parse(startDate);
        JsonFactory jsonFactory = new JsonFactory();
        StringWriter stringWriter = new StringWriter();
        try(JsonGenerator jsonGenerator = jsonFactory.createGenerator(stringWriter)) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeArrayFieldStart("200dma");
            for (int index = 0; index < symbols.length && index < MAX_NUMBER_OF_SYMBOLS; index++) {
                final String symbol = symbols[index];
                Ticker ticker = this.lruCache.get(symbol);
                if(ticker == null){
                    ticker = this.repository.findBySymbol(symbol);
                    this.lruCache.set(symbol, ticker);

                }
                Double dma = calculateDma200(sDate, sDate.plusDays(NUMBER_OF_DAYS), ticker);
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("Ticker", symbol);
                jsonGenerator.writeStringField("Avg", String.format("%.4f", dma));
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
            jsonGenerator.close();
            return stringWriter.toString();
        }catch (IOException e){
            throw new DataAccessException(e);
        }
    }

    private String getDma200JSONResponse(String symbol, Double dma) {
        JsonFactory jsonFactory = new JsonFactory();
        StringWriter stringWriter = new StringWriter();
        try(JsonGenerator jsonGenerator = jsonFactory.createGenerator(stringWriter)) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName("200dma");
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("Ticker", symbol);
            jsonGenerator.writeStringField("Avg", String.format("%.4f", dma));
            jsonGenerator.writeEndObject();
            jsonGenerator.close();
        }catch(IOException e){
            throw new DataAccessException(e);
        }

        return stringWriter.toString();
    }

    private Double calculateDma200(LocalDate startDate, LocalDate endDate, Ticker ticker) {
        List<TickerData> tickerDataList = ticker.getTickerData().stream()
                .filter(t -> t.getDate().compareTo(endDate) >= 0 && t.getDate().compareTo(startDate) <= 0)
                .collect(Collectors.toList());
        Double dma = 0.0;

        int count = tickerDataList.size();
        int totalCount = 0;
        for(TickerData tickerData: tickerDataList) {
             dma += (tickerData.getClose() * count);
             totalCount += count--;
        }
        return dma/totalCount;
    }
}
