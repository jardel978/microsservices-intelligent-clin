package com.intelligentclin.clinic_service.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String firstName;

    private String lastName;

    private String cpf;

    private String phone;
}
