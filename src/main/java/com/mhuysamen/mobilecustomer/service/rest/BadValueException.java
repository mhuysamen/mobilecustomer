package com.mhuysamen.mobilecustomer.service.rest;

public class BadValueException extends RuntimeException {
    public BadValueException(final String parameterName, final String parameterValue) {
        super("Parameter '%s' doesn't support the value supplied: %s".formatted(parameterName, parameterValue));
    } 
    
}
