package com.intelligentclin.clinic_service.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record PersonReq(

        Long uid,

        @NotNull(message = "The first name is required.") String firstName,

        @NotNull(message = "The last name is required.") String lastName,

        // CPF format: 000.000.000-00
        @Pattern(regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)", message = "Invalid CPF format. Expected: 000.000.000-00") @NotNull(message = "CPF is required.") String cpf,

        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime createdAt,

        @NotNull(message = "Email is required.") @Email(message = "Invalid email format. Example: myemail@example.com") String email,

        @NotNull(message = "Phone number is required.") @Pattern(regexp = "^\\(?(?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$", message = "Invalid phone number format. Try: xx xxxxx-xxxx and make sure the area code is correct.") String phone

) {
}
