package com.intelligentclin.clinic_service.entity;

import com.intelligentclin.clinic_service.entity.enums.Specialty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
