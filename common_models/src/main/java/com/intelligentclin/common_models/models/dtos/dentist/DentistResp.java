package com.intelligentclin.common_models.models.dtos.dentist;

import java.time.LocalDateTime;
import java.util.List;

import com.intelligentclin.common_models.models.dtos.PersonResp;
import com.intelligentclin.common_models.models.enums.Specialty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DentistResp extends PersonResp {

        private String uid;

        private LocalDateTime createdAt;

        private String registrationNumber;

        private List<Specialty> specialties;
}