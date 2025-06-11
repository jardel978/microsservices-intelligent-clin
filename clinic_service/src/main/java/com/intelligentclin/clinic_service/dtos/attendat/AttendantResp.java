package com.intelligentclin.clinic_service.dtos.attendat;

import java.time.LocalDateTime;

public record AttendantResp(
        
        Long uid,

        String firstName,

        String lastName,

        // CPF format: 000.000.000-00
        String cpf,

        LocalDateTime createdAt,

        String email
        
) {}