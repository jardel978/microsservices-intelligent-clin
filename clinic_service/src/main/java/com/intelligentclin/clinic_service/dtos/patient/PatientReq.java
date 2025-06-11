package com.intelligentclin.clinic_service.dtos.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intelligentclin.clinic_service.entity.Address;
import com.intelligentclin.clinic_service.entity.Age;
import com.intelligentclin.clinic_service.entity.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatientReq(

        Long uid,

        String firstName,

        String lastName,

        String cpf,

        LocalDateTime createdAt,

        String email,

        String phone,

        @JsonFormat(pattern = "dd-MM-yyyy") LocalDate birthDate,

        Age age,

        Address address,

        Gender gender

) {
}
