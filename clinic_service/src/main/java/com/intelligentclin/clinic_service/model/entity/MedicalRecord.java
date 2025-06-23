package com.intelligentclin.clinic_service.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "medical_records")
public class MedicalRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String uid;

    private String patientUid;

    private String dentistUid;

    private String treatmentPlan;

    private String treatmentProgress;

    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
   
}
