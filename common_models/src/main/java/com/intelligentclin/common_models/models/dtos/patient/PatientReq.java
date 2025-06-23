package com.intelligentclin.common_models.models.dtos.patient;

import com.intelligentclin.common_models.models.Address;
import com.intelligentclin.common_models.models.Age;
import com.intelligentclin.common_models.models.dtos.PersonReq;
import com.intelligentclin.common_models.models.enums.Gender;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatientReq extends PersonReq {

        private LocalDate birthDate;

        private Age age;

        private Address address;

        private Gender gender;

        public PatientReq(String uid, String firstName, String lastName, String cpf, String email, String phone,
                        LocalDate birthDate, Age age, Address address, Gender gender) {
                super(uid, firstName, lastName, cpf, email, phone);
                this.birthDate = birthDate;
                this.age = age;
                this.address = address;
                this.gender = gender;
        }

        
}
