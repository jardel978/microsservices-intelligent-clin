package com.intelligentclin.clinic_service.model.dtos.converters;

import org.springframework.stereotype.Component;

import com.intelligentclin.common_models.models.dtos.medicalRecord.MedicalRecordReq;
import com.intelligentclin.common_models.models.dtos.medicalRecord.MedicalRecordResp;
import com.intelligentclin.clinic_service.model.entity.MedicalRecord;

@Component
public class MedicalRecordModelMapperConverter extends GenericModelMapperConverter<MedicalRecord, MedicalRecordReq, MedicalRecordResp> {

}
