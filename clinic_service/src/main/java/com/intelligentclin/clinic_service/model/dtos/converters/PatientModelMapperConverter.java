package com.intelligentclin.clinic_service.model.dtos.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import com.intelligentclin.clinic_service.model.entity.Patient;
import com.intelligentclin.common_models.models.dtos.patient.PatientReq;
import com.intelligentclin.common_models.models.dtos.patient.PatientResp;

@Component
@NoArgsConstructor
public class PatientModelMapperConverter extends GenericModelMapperConverter<Patient, PatientReq, PatientResp> {

}
