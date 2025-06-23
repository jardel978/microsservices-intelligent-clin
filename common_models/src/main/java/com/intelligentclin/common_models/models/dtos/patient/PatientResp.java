package com.intelligentclin.common_models.models.dtos.patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.intelligentclin.common_models.models.Address;
import com.intelligentclin.common_models.models.Age;
import com.intelligentclin.common_models.models.dtos.PersonResp;
import com.intelligentclin.common_models.models.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientResp extends PersonResp {

        private LocalDateTime createdAt;

        private LocalDate birthDate;

        private Age age;

        private Address address;

        private Gender gender;

}