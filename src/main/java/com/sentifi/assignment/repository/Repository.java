package com.sentifi.assignment.repository;

import com.sentifi.assignment.domain.Ticker;

/**
 * Created by Sujay on 4/7/17.
 */

public interface Repository {
    Ticker findBySymbol(String symbol);

}
