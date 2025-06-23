package com.intelligentclin.common_models.models.enums;

public enum Gender {

    MALE("male", 1),
    FEMALE("female", 2),
    OTHER("other", 3);

    private final String name;
    private final int code;

    Gender(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
