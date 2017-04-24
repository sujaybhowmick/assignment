package com.sentifi.assignment.exceptions;

/**
 * Created by Sujay on 4/14/17.
 */
public class DataAccessException extends RuntimeException {
    public DataAccessException(Throwable t){
        super(t);
    }

    public DataAccessException(String message){
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}