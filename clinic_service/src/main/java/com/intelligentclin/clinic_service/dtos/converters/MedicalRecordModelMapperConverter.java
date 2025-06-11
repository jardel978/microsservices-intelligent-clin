package com.intelligentclin.clinic_service.dtos.converters;

import com.intelligentclin.clinic_service.dtos.medicalRecord.MedicalRecordReq;
import com.intelligentclin.clinic_service.dtos.medicalRecord.MedicalRecordResp;
import com.intelligentclin.clinic_service.entity.MedicalRecord;

import org.springframework.stereotype.Component;

@Component
public class MedicalRecordModelMapperConverter extends GenericModelMapperConverter<MedicalRecord, MedicalRecordReq, MedicalRecordResp> {

}
