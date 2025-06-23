package com.intelligentclin.common_models.models.dtos.attendat;

import java.time.LocalDateTime;

import com.intelligentclin.common_models.models.dtos.PersonResp;
import com.intelligentclin.common_models.models.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendantResp extends PersonResp {
    private Gender gender;

    public AttendantResp(String uid, String firstName, String lastName, String cpf, LocalDateTime createdAt,
            String email, String phone, Gender gender) {
        super(uid, firstName, lastName, cpf, createdAt, email, phone);
        this.gender = gender;
    }
    
    
}