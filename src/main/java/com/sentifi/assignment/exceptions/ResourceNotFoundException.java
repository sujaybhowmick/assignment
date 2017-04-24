package com.sentifi.assignment.exceptions;

/**
 * Created by Sujay on 4/16/17.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg){
        super(msg);
    }

    public ResourceNotFoundException(Throwable e){
        super(e);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
