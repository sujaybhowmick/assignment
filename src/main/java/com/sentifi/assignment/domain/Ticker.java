package com.sentifi.assignment.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sujay on 4/14/17.
 */
public class Ticker extends  Entity {

    private final String symbol;
    private List<TickerData> tickerData;


    public Ticker(String symbol){
        this.symbol = symbol;
        this.tickerData = new ArrayList<>();
    }

    public void addTickerData(TickerData tickerData){
        this.tickerData.add(tickerData);
    }

    public void addAll(List<TickerData> tickerDataList) {
        this.tickerData.addAll(tickerDataList);
    }

    public List<TickerData> getTickerData() {
        return tickerData;
    }

    public String getSymbol() {
        return symbol;
    }
}
