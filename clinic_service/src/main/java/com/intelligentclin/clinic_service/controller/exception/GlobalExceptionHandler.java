package com.intelligentclin.clinic_service.controller.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<Map<String, String>>> handleValidationException(WebExchangeBindException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return Mono.just(ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(errors));
    }

    
}