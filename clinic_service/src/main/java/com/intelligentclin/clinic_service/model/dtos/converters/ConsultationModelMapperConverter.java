package com.intelligentclin.clinic_service.model.dtos.converters;

import org.springframework.stereotype.Component;

import com.intelligentclin.common_models.models.dtos.consultation.ConsultationReq;
import com.intelligentclin.common_models.models.dtos.consultation.ConsultationResp;
import com.intelligentclin.clinic_service.model.entity.Consultation;

@Component
public class ConsultationModelMapperConverter extends GenericModelMapperConverter<Consultation, ConsultationReq,
        ConsultationResp> {

}
