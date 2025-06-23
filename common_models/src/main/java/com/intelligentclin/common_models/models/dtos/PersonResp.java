package com.intelligentclin.common_models.models.dtos;

import java.time.LocalDateTime;

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
public class PersonResp {

        private String uid;

        private String firstName;

        private String lastName;

        private String cpf;

        private LocalDateTime createdAt;

        private String email;

        private String phone;
}
