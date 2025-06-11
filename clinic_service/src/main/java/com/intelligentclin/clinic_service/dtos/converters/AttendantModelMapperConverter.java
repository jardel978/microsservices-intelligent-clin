package com.intelligentclin.clinic_service.dtos.converters;

import com.intelligentclin.clinic_service.dtos.attendat.AttendantReq;
import com.intelligentclin.clinic_service.dtos.attendat.AttendantResp;
import com.intelligentclin.clinic_service.entity.Attendant;

import org.springframework.stereotype.Component;

@Component
public class AttendantModelMapperConverter extends GenericModelMapperConverter<Attendant, AttendantReq, AttendantResp> {

}
