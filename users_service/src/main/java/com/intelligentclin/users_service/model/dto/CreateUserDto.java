package com.intelligentclin.users_service.model.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

import com.intelligentclin.common_models.models.Age;
import com.intelligentclin.common_models.models.enums.Gender;
import com.intelligentclin.common_models.models.enums.Specialty;
import com.intelligentclin.users_service.model.enums.TypeUser;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
public class CreateUserDto {

    @NotNull(message = "The username is required.") 
    private String username;

    @NotNull(message = "The password is required.") @Size(min = 6, max = 15, message = "password must be longer than 6 characters.") 
    private String password;
    
    private TypeUser type;

    private String firstName;

    private String lastName;

    private String cpf;

    private String email;

    private String phone;

    private LocalDate birthDate;

    private Age age;

    private Address address;

    private Gender gender;

    private String registrationNumber;

    private List<Specialty> specialties;
}