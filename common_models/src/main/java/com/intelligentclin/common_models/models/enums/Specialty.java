package com.intelligentclin.common_models.models.enums;

public enum Specialty {

    PEDIATRIC_DENTISTRY("pediatric dentistry", "DNT001"),
    IMPLANT_DENTISTRY("implant dentistry", "DNT002"),
    RADIOLOGY("radiology", "DNT003"),
    ORTHODONTICS("orthodontics", "DNT004"),
    ENDODONTICS("endodontics", "DNT005"),
    OPERATIVE_DENTISTRY("operative dentistry", "DNT006"),
    GENERAL_DENTISTRY("general dentistry", "DNT007"),
    PROSTHODONTICS("prosthodontics", "DNT008");

    private final String name;
    private final String code;

    Specialty(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
