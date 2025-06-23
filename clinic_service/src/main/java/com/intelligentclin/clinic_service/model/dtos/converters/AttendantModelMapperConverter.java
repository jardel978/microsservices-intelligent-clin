package com.intelligentclin.clinic_service.model.dtos.converters;

import org.springframework.stereotype.Component;

import com.intelligentclin.common_models.models.dtos.attendat.AttendantReq;
import com.intelligentclin.common_models.models.dtos.attendat.AttendantResp;
import com.intelligentclin.clinic_service.model.entity.Attendant;

@Component
public class AttendantModelMapperConverter extends GenericModelMapperConverter<Attendant, AttendantReq, AttendantResp> {

}
