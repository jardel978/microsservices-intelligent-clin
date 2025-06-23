package com.intelligentclin.common_models.models.dtos.consultation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intelligentclin.common_models.models.enums.ConsultationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationSummaryResp {

        private String uid;
       
        private LocalDate consultationDate;

        private LocalTime consultationTime;

        private String notes;

        private Double amount;

        private ConsultationStatus status;

        private @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime statusUpdatedAt;
}
