package com.sentifi.assignment.repository;

import com.sentifi.assignment.domain.DataSet;
import com.sentifi.assignment.domain.Ticker;
import com.sentifi.assignment.domain.TickerData;
import com.sentifi.assignment.exceptions.DataAccessException;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sujay on 4/14/17.
 */
public abstract class AbstractRepository implements Repository {

    protected static final Map<Integer, String> colIndexMapping = new HashMap<>();
    protected static final Map<String, Class<?>> colTypeMapping = new HashMap<>();

    static {

        colIndexMapping.put(0, "Date");
        colIndexMapping.put(1, "Open");
        colIndexMapping.put(2, "High");
        colIndexMapping.put(3, "Low");
        colIndexMapping.put(4, "Close");

        colTypeMapping.put("Date", LocalDate.class);
        colTypeMapping.put("Open", Double.class);
        colTypeMapping.put("High", Double.class);
        colTypeMapping.put("Low", Double.class);
        colTypeMapping.put("Close", Double.class);
    }



    protected void setProperty(Object object, TickerData tickerSymbol, String strMethod) throws Exception {
        Method method = null;
        final String setMethod = "set" + strMethod;
        Class<?> type = colTypeMapping.get(strMethod);
        if(LocalDate.class.equals(type)){
            method = TickerData.class.getMethod(setMethod, type);
            method.invoke(tickerSymbol, LocalDate.parse(String.valueOf(object)));
        }
        if(Double.class.equals(type)){
            method = TickerData.class.getMethod(setMethod, type);
            method.invoke(tickerSymbol, Double.parseDouble(String.valueOf(object)));
        }
    }


    protected List<TickerData> convertToTickerDataList(DataSet dataSet) throws Exception {

        List<Object> data = dataSet.getData();
        List<TickerData>  tickers = new ArrayList<>();
        for(int index = 0; index < data.size(); index++) {
            List<Object> items = (List<Object>)data.get(index);
            TickerData tickerData = new TickerData();
            int itemIndex = 0;
            do {
                setProperty(items.get(itemIndex), tickerData, colIndexMapping.get(itemIndex));
                itemIndex++;
            }while (itemIndex < items.size());
            tickers.add(tickerData);
        }
        return tickers;
    }

    protected Ticker getTicketData(String symbol, DataSet dataSet){

        Ticker ticker = new Ticker(symbol);
        try {
            List<TickerData> tickerDataList = convertToTickerDataList(dataSet);
            ticker.addAll(tickerDataList);
            return ticker;
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }
}
