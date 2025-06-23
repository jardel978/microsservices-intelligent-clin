package com.intelligentclin.clinic_service.model.dtos.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import com.intelligentclin.common_models.models.dtos.dentist.DentistReq;
import com.intelligentclin.common_models.models.dtos.dentist.DentistResp;
import com.intelligentclin.clinic_service.model.entity.Dentist;

@Component
@NoArgsConstructor
public class DentistModelMapperConverter extends GenericModelMapperConverter<Dentist, DentistReq, DentistResp> {

}
