package com.intelligentclin.clinic_service.dtos.patient;

import com.intelligentclin.clinic_service.entity.Address;
import com.intelligentclin.clinic_service.entity.Age;
import com.intelligentclin.clinic_service.entity.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatientResp(

        Long uid,

        String firstName,

        String lastName,

        String cpf,

        LocalDateTime createdAt,

        String email,

        String phone,

        LocalDate birthDate,

        Age age,

        Address address,

        Gender gender

) {
}
