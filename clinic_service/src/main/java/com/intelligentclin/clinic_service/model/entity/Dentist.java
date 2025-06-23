package com.intelligentclin.clinic_service.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.intelligentclin.common_models.models.enums.Specialty;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "dentists")
public class Dentist extends Person {

    private static final long serialVersionUID = 1L;

    @Id
    private String uid;

    private String registrationNumber;

    private List<Specialty> specialties;

    private Set<String> appointmentUids;

    private Set<String> recordUids;
}
