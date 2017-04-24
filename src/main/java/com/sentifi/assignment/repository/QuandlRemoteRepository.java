package com.sentifi.assignment.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.sentifi.assignment.domain.DataSet;
import com.sentifi.assignment.domain.Ticker;
import com.sentifi.assignment.exceptions.DataAccessException;
import com.sentifi.assignment.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.SimpleDateFormat;


/**
 * Created by Sujay on 4/21/17.
 */
@org.springframework.stereotype.Repository(value = "QuandlRemoteRepository")
public class QuandlRemoteRepository extends AbstractRepository {

    @Value("${api.baseUrl}") String baseUrl;

    @Value("${api.endPoint}") String apiEndPoint;

    @Override
    public Ticker findBySymbol(String symbol) {
        final String apiUrl = baseUrl + apiEndPoint + "/" + symbol + ".json";
        GetRequest getRequest = Unirest.get(apiUrl);
        try {
            HttpResponse<String> httpResponseStr = getRequest.asString();
            DataSet dataSet = load(symbol, httpResponseStr.getBody());
            return getTicketData(symbol, dataSet);
        } catch (UnirestException e) {
            throw new DataAccessException(e);
        }
    }

    private DataSet load(String symbol, String content) {
        if(content == null){
            throw new DataAccessException("Missing data for symbol");
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true)
                    .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

            return mapper.readValue(content, DataSet.class);
        } catch (DataAccessException | IOException e) {
            throw new ResourceNotFoundException(String.format("%s Symbol not found", symbol), e);
        }
    }
}
