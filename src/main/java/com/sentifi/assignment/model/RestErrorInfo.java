package com.sentifi.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Sujay on 4/14/17.
 */
public class RestErrorInfo {
    private  String detail;
    private  String timeStamp;
    private  String message;

    private static DateFormat df = new SimpleDateFormat(
            "yyyy-MMM-dd HH:mm:ss z");

    static {
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public RestErrorInfo(Exception ex, String detail) {
        this.timeStamp = df.format(new Date());
        this.message = ex.getLocalizedMessage();
        this.detail = detail;
    }

    @JsonProperty("detail")
    public String getDetail() {
        return detail;
    }

    @JsonProperty("timestamp")
    public String getTimeStamp() {
        return timeStamp;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}