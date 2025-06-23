package com.intelligentclin.common_models.models.dtos.medicalRecord;

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
public class MedicalRecordResp {

        private String uid;

        private String patientUid;

        private String dentistUid;

        private String treatmentPlan;

        private String treatmentProgress;

        private LocalDateTime createdAt;

        private LocalDateTime lastModifiedAt;       
}
