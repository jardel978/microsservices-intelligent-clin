package com.intelligentclin.clinic_service.dtos.attendat;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AttendantReq(
        
        Long uid,

        @NotNull(message = "The first name is required.") String firstName,

        @NotNull(message = "The last name is required.") String lastName,

        // CPF format: 000.000.000-00
        @Pattern(regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)", message = "Invalid CPF format. Expected: 000.000.000-00") @NotNull(message = "CPF is required.") String cpf,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime createdAt,

        @NotNull(message = "Email is required.") @Email(message = "Invalid email format. Example: myemail@example.com") String email
        
) {}