package com.intelligentclin.common_models.models.dtos.medicalRecord;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
public class MedicalRecordReq {
        
        private String uid;

        @NotNull(message = "The patient id is required.") 
        private String patientUid;

        @NotNull(message = "The dentist id is required.") 
        private String dentistUid;

        private String treatmentPlan;

        private String treatmentProgress;

        private LocalDateTime createdAt;

        private LocalDateTime lastModifiedAt;       
}
