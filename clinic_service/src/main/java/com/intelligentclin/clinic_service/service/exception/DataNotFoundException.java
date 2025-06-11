package com.intelligentclin.clinic_service.service.exception;


public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String mensagem) {
        super(mensagem);
    }
}
