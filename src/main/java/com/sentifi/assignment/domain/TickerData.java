package com.sentifi.assignment.domain;

import java.time.LocalDate;

/**
 * Created by Sujay on 4/14/17.
 */
public class TickerData extends Entity {
    private LocalDate date;

    private Double open;

    private Double high;

    private Double low;

    private Double close;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }
}
