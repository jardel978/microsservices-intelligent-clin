package com.intelligentclin.common_models.models;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Age implements Serializable {

    private static final long serialVersionUID = 1L;

    private int days;
    private int months;
    private int years;
}
