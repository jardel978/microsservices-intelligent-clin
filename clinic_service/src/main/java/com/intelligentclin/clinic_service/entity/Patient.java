package com.intelligentclin.clinic_service.entity;

import com.intelligentclin.clinic_service.entity.enums.Gender;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Transient;

import java.time.LocalDate;

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

    @Transient
    private Age age;

    private Address address;

    private String medicalRecordUid;

    private Gender gender;
}
