package com.intelligentclin.clinic_service.dtos.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intelligentclin.clinic_service.entity.enums.ConsultationStatus;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ConsultationReq(

        String uid,

        @NotNull(message = "Please provide the patient for this consultation.") String patientUid,

        @NotNull(message = "The dentist responsible for the consultation must be specified.") String dentistUid,

        @NotNull(message = "You must inform the attendant registering this consultation.") String attendantUid,

        @NotNull(message = "Consultation date is required.") LocalDate consultationDate,

        @NotNull(message = "Consultation time is required.") @JsonFormat(pattern = "HH:mm") LocalTime consultationTime,

        String notes,

        @Digits(fraction = 2, integer = 4, message = "Please enter a valid amount. Example: 100.00") Double amount,

        ConsultationStatus status,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime statusUpdatedAt
) {}
