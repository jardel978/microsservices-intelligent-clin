package com.intelligentclin.common_models.models.dtos.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intelligentclin.common_models.models.dtos.PersonSummaryResp;
import com.intelligentclin.common_models.models.enums.ConsultationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationResp {

        private String uid;

        private PersonSummaryResp patient;

        private PersonSummaryResp dentist;

        private PersonSummaryResp attendant;

        private LocalDate consultationDate;

        private LocalTime consultationTime;

        private String notes;

        private Double amount;

        private ConsultationStatus status;

        private @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime statusUpdatedAt;
}