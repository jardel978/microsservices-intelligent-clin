package com.intelligentclin.clinic_service.service.exception;

public class RequiredParamException extends RuntimeException {

    public RequiredParamException(String mensagem) {
        super(mensagem);
    }
}
