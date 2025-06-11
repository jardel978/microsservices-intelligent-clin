package com.intelligentclin.clinic_service.dtos.converters;

import com.intelligentclin.clinic_service.dtos.patient.PatientReq;
import com.intelligentclin.clinic_service.dtos.patient.PatientResp;
import com.intelligentclin.clinic_service.entity.Patient;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PatientModelMapperConverter extends GenericModelMapperConverter<Patient, PatientReq, PatientResp> {

}
