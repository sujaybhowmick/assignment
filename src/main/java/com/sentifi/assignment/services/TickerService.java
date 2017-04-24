package com.sentifi.assignment.services;

/**
 * Created by Sujay on 4/11/17.
 */

public interface TickerService {
    String closePrice(String symbol, String startDate, String endDate);

    String dma200(String symbol, String startDate);

    String dma200Bulk(String[] symbols, String startDate);
}
