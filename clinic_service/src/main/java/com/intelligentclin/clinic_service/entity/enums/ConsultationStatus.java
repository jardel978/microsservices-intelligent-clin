package com.intelligentclin.clinic_service.entity.enums;

public enum ConsultationStatus {

    PENDING("pending", 1),
    CONFIRMED("confirmed", 2),
    COMPLETED("completed", 3),
    CANCELED("canceled", 4);

    private final String status;
    private final int code;

    ConsultationStatus(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }
}
