package com.mhuysamen.mobilecustomer.service.rest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.mhuysamen.mobilecustomer.core.CustomerAlreadyExistsException;
import com.mhuysamen.mobilecustomer.core.CustomerNotFoundException;
import com.mhuysamen.mobilecustomer.core.MobileSubscriberAlreadyExistsException;
import com.mhuysamen.mobilecustomer.core.MobileSubscriberNotFoundException;
import com.mhuysamen.mobilecustomer.domain.IDCard.InvalidIDCardException;
import com.mhuysamen.mobilecustomer.domain.PhoneNumber.InvalidPhoneNumber;

@RestControllerAdvice
public class MobileCustomerControllerAdvice {
    
    final static List<String> emptyList;

    static {
        emptyList = new ArrayList<>();
    }

    private ErrorMessageV1 createErrorMessageV1(HttpStatus status, MethodArgumentNotValidException ex) {
        StackTraceElement traceElement = ex.getStackTrace()[0];
        // ex.printStackTrace();
        String traceMessage = "%s:%d".formatted(traceElement.getFileName(), traceElement.getLineNumber());
        List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
        return new ErrorMessageV1(status, Instant.now(), ex.getClass().getSimpleName(), "Field validation errors found!", traceMessage, errors);
    }

    private ErrorMessageV1 createErrorMessageV1(HttpStatus status, Exception ex) {
        StackTraceElement traceElement = ex.getStackTrace()[0];
        // ex.printStackTrace();
        String traceMessage = "%s:%d".formatted(traceElement.getFileName(), traceElement.getLineNumber());
        return new ErrorMessageV1(status, Instant.now(), ex.getClass().getSimpleName(), ex.getMessage(), traceMessage, emptyList);
    }

    @ExceptionHandler(value = {CustomerAlreadyExistsException.class, MobileSubscriberAlreadyExistsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageV1 coreBadRequest(Exception ex, WebRequest request) {
        return createErrorMessageV1(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {CustomerNotFoundException.class, MobileSubscriberNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageV1 coreNotFound(Exception ex, WebRequest request) {
        return createErrorMessageV1(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(value = {
        InvalidIDCardException.class,
        InvalidPhoneNumber.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageV1 domainBadRequest(Exception ex, WebRequest request) {
        return createErrorMessageV1(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {
        BadValueException.class,
        MissingParameterException.class,
        HttpMessageNotReadableException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageV1 serviceBadRequest(Exception ex, WebRequest request) {
        return createErrorMessageV1(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {
        MethodArgumentNotValidException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageV1 serviceValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        return createErrorMessageV1(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {
        NumberFormatException.class,
        IllegalAccessException.class,
        NullPointerException.class
    })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageV1 serviceInternalError(Exception ex, WebRequest request) {
        return createErrorMessageV1(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

}
