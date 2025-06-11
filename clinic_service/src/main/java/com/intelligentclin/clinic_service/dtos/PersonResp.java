package com.intelligentclin.clinic_service.dtos;

import java.time.LocalDateTime;

public record PersonResp(

        Long uid,

        String firstName,

        String lastName,

        String cpf,

        LocalDateTime createdAt,

        String email,

        String phone

) {}
