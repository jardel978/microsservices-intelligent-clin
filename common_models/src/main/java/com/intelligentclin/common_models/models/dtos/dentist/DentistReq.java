package com.intelligentclin.common_models.models.dtos.dentist;

import com.intelligentclin.common_models.models.dtos.PersonReq;
import com.intelligentclin.common_models.models.enums.Specialty;

import jakarta.validation.constraints.NotNull;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DentistReq extends PersonReq {

        @NotNull(message = "The dentist's registration number is required.") 
        private String registrationNumber;

        private List<Specialty> specialties;

        public DentistReq(String uid, String firstName, String lastName, String cpf, String email, String phone,
                @NotNull(message = "The dentist's registration number is required.") String registrationNumber,
                List<Specialty> specialties) {
            super(uid, firstName, lastName, cpf, email, phone);
            this.registrationNumber = registrationNumber;
            this.specialties = specialties;
        }

        
}
