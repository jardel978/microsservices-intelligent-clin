package com.intelligentclin.clinic_service.dtos.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import com.intelligentclin.clinic_service.dtos.dentist.DentistReq;
import com.intelligentclin.clinic_service.dtos.dentist.DentistResp;
import com.intelligentclin.clinic_service.entity.Dentist;

@Component
@NoArgsConstructor
public class DentistModelMapperConverter extends GenericModelMapperConverter<Dentist, DentistReq, DentistResp> {

}
