package com.intelligentclin.clinic_service.dtos.medicalRecord;

import java.time.LocalDateTime;

public record MedicalRecordResp(

        String uid,

        String patientUid,

        String dentistUid,

        String treatmentPlan,

        String treatmentProgress,

        LocalDateTime createdAt,

        LocalDateTime lastModifiedAt
       
) {}
