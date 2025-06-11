package com.intelligentclin.clinic_service.dtos.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intelligentclin.clinic_service.entity.enums.ConsultationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ConsultationSummaryResp(

        String uid,
       
        LocalDate consultationDate,

        LocalTime consultationTime,

        String notes,

        Double amount,

        ConsultationStatus status,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime statusUpdatedAt

) {}
