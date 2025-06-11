package com.intelligentclin.clinic_service.entity;

import com.intelligentclin.clinic_service.entity.enums.ConsultationStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Consultations")
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String uid;

    private String patientUid;

    private String dentistUid;

    private String attendantUid;

    private LocalDate consultationDate;

    private LocalTime consultationTime;

    private String notes;

    private Double amount;

    private ConsultationStatus status;

    private LocalDateTime statusUpdatedAt;
}
