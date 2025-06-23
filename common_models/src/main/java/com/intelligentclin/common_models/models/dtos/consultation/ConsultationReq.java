package com.intelligentclin.common_models.models.dtos.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intelligentclin.common_models.models.enums.ConsultationStatus;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationReq {

        private String uid;

        @NotNull(message = "Please provide the patient for this consultation.") 
        private String patientUid;

        @NotNull(message = "The dentist responsible for the consultation must be specified.") 
        private String dentistUid;

        @NotNull(message = "You must inform the attendant registering this consultation.") 
        private String attendantUid;

        @NotNull(message = "Consultation date is required.") 
        private LocalDate consultationDate;

        @NotNull(message = "Consultation time is required.") @JsonFormat(pattern = "HH:mm") 
        private LocalTime consultationTime;

        private String notes;

        @Digits(fraction = 2, integer = 4, message = "Please enter a valid amount. Example: 100.00") 
        private Double amount;

        private ConsultationStatus status;

}
