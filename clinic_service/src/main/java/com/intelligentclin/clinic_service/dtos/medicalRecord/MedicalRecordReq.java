package com.intelligentclin.clinic_service.dtos.medicalRecord;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record MedicalRecordReq(
        
        String uid,

        String patientUid,

        String dentistUid,

        String treatmentPlan,

        String treatmentProgress,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime createdAt,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime lastModifiedAt
       
) {}
