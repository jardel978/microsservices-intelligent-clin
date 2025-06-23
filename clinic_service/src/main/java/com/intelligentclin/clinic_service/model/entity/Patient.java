package com.intelligentclin.clinic_service.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.intelligentclin.common_models.models.Address;
import com.intelligentclin.common_models.models.Age;
import com.intelligentclin.common_models.models.enums.Gender;

import org.springframework.data.annotation.Transient;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "patients")
public class Patient extends Person {

    private static final long serialVersionUID = 1L;

    @Id
    private String uid;

    private LocalDate birthDate;

    private LocalDateTime createdAt;

    @Transient
    private Age age;

    private Address address;

    private String medicalRecordUid;

    private Gender gender;
}
