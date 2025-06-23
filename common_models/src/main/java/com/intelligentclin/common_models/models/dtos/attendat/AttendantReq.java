package com.intelligentclin.common_models.models.dtos.attendat;

import com.intelligentclin.common_models.models.dtos.PersonReq;
import com.intelligentclin.common_models.models.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttendantReq extends PersonReq {
    
    private Gender gender;

    public AttendantReq(String uid, String firstName, String lastName, String cpf, String email, String phone,
            Gender gender) {
        super(uid, firstName, lastName, cpf, email, phone);
        this.gender = gender;
    }

    
}