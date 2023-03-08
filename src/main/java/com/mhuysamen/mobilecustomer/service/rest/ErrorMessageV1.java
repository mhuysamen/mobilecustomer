package com.mhuysamen.mobilecustomer.service.rest;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorMessageV1 {
    public HttpStatus status;
    public Instant timestamp;
    public String error;
    public String message;
    public String source;
    public List<String> validationErrors;
}
