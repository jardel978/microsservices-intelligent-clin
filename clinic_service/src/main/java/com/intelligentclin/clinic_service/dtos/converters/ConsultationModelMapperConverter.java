package com.intelligentclin.clinic_service.dtos.converters;

import com.intelligentclin.clinic_service.dtos.consultation.ConsultationReq;
import com.intelligentclin.clinic_service.dtos.consultation.ConsultationResp;
import com.intelligentclin.clinic_service.entity.Consultation;

import org.springframework.stereotype.Component;

@Component
public class ConsultationModelMapperConverter extends GenericModelMapperConverter<Consultation, ConsultationReq,
        ConsultationResp> {

}
