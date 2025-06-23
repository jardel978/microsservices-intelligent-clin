package com.intelligentclin.users_service.model.enums;

public enum TypeUser {

    ATTENDANT("attendant", 1),
    DENTIST("dentist", 2);

    private final String name;
    private final int code;

    TypeUser(String name, int Code) {
        this.name = name;
        this.code = Code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
