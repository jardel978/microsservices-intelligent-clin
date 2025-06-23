package com.intelligentclin.clinic_service.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "attendants")
public class Attendant extends Person {

    private static final long serialVersionUID = 1L;

    @Id
    private String uid;
    
}
