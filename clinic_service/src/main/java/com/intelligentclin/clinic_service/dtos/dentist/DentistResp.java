package com.intelligentclin.clinic_service.dtos.dentist;

import com.intelligentclin.clinic_service.entity.enums.Specialty;

import java.time.LocalDateTime;
import java.util.List;

public record DentistResp(

        String uid,

        String firstName,

        String lastName,

        // CPF format: 000.000.000-00
        String cpf,

        LocalDateTime createdAt,

        String email,

        String phone,
        
        String registrationNumber,

        List<Specialty> specialties

) {
}
