package com.intelligentclin.common_models.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonReq {

        private String uid;

        @NotNull(message = "The first name is required.") 
        private String firstName;

        @NotNull(message = "The last name is required.") 
        private String lastName;

        // CPF format: 000.000.000-00
        @Pattern(regexp = "(^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$)", message = "Invalid CPF format. Expected: 000.000.000-00") @NotNull(message = "CPF is required.") 
        private String cpf;

        @NotNull(message = "Email is required.") @Email(message = "Invalid email format. Example: myemail@example.com") 
        private String email;

        @NotNull(message = "Phone number is required.") @Pattern(regexp = "^\\(?(?:[14689][1-9]|2[12478]|3[1234578]|5[1345]|7[134579])\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$", message = "Invalid phone number format. Try: xx xxxxx-xxxx and make sure the area code is correct.") 
        private String phone;
}
