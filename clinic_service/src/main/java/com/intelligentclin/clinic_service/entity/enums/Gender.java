package com.intelligentclin.clinic_service.entity.enums;

public enum Gender {

    MALE("male", 1),
    FEMALE("female", 2),
    OTHER("other", 3);

    private final String name;
    private final int codigo;

    Gender(String name, int codigo) {
        this.name = name;
        this.codigo = codigo;
    }

    public String getName() {
        return name;
    }

    public int getCodigo() {
        return codigo;
    }
}
