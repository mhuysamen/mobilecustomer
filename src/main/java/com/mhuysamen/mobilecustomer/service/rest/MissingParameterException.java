package com.mhuysamen.mobilecustomer.service.rest;

public class MissingParameterException extends RuntimeException {
    public MissingParameterException(final String parameterName) {
        super("The parameter '%s' is missing!".formatted(parameterName));
    }
    
}
