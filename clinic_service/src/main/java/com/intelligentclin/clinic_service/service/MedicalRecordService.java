package com.intelligentclin.clinic_service.service;

import com.intelligentclin.clinic_service.model.dtos.converters.MedicalRecordModelMapperConverter;
import com.intelligentclin.common_models.models.dtos.medicalRecord.MedicalRecordReq;
import com.intelligentclin.common_models.models.dtos.medicalRecord.MedicalRecordResp;
import com.intelligentclin.clinic_service.model.entity.Dentist;
import com.intelligentclin.clinic_service.model.entity.MedicalRecord;
import com.intelligentclin.clinic_service.model.entity.Patient;
import com.intelligentclin.clinic_service.repository.IDentistRepository;
import com.intelligentclin.clinic_service.repository.IPatientRepository;
import com.intelligentclin.clinic_service.repository.IMedicalRecordRepository;
import com.intelligentclin.clinic_service.service.exception.DataNotFoundException;
import com.intelligentclin.clinic_service.service.exception.MedicalRecordException;
import com.intelligentclin.clinic_service.service.utils.UtilDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MedicalRecordService {

    @Autowired
    private IMedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalRecordModelMapperConverter medicalRecordConverter;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IDentistRepository dentistRepository;

    @Autowired
    private UtilDate dateUtils;

    // Save Medical Record (MongoDB - String IDs)
    public void save(MedicalRecordReq medicalRecordReq) {
        MedicalRecord medicalRecord = medicalRecordConverter.mapModelReqToEntity(medicalRecordReq, MedicalRecord.class);

        Patient patient = patientRepository.findById(medicalRecordReq.getPatientUid())
                .orElseThrow(() -> new DataNotFoundException("The specified Patient was not found in the database."));

        patient.setAge(dateUtils.generate(patient.getBirthDate(), LocalDate.now()));

        Dentist dentist = dentistRepository.findById(medicalRecordReq.getDentistUid())
                .orElseThrow(() -> new DataNotFoundException("The specified Dentist was not found in the database."));

        try {
            if (patient.getMedicalRecordUid() == null) {
                medicalRecord.setPatientUid(patient.getUid());
                medicalRecord.setDentistUid(dentist.getUid());
                patient.setMedicalRecordUid(medicalRecord.getUid());
            } else {
                MedicalRecord existingRecord = medicalRecordRepository.findById(patient.getMedicalRecordUid())
                        .orElseThrow(() -> new DataNotFoundException(
                                "The specified medical record was not found in the database."));
                existingRecord.setDentistUid(medicalRecordReq.getDentistUid());
                existingRecord.setTreatmentPlan(medicalRecordReq.getTreatmentPlan());
                existingRecord.setTreatmentProgress(medicalRecordReq.getTreatmentProgress());
            }
            patientRepository.save(patient);           
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    // Find Medical Record by ID
    public Optional<MedicalRecordResp> findById(String id) throws DataNotFoundException {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Medical Record not found."));

        Patient patient = patientRepository.findById(medicalRecord.getPatientUid())
                .orElseThrow(() -> new DataNotFoundException("The specified Patient was not found in the database."));
        if (patient != null)
            patient.setAge(dateUtils.generate(patient.getBirthDate(), LocalDate.now()));

        return Optional
                .ofNullable(medicalRecordConverter.mapEntityToModelResp(medicalRecord, MedicalRecordResp.class));
    }

    // Find all Medical Records with pagination
    public Page<MedicalRecordResp> findAll(Pageable pageable) {
        Page<MedicalRecord> medicalRecordPage = medicalRecordRepository.findAll(pageable);
        return medicalRecordPage.map(medicalRecord -> {
            return medicalRecordConverter.mapEntityToModelResp(medicalRecord, MedicalRecordResp.class);
        });
    }

    // Delete Medical Record by ID
    public void deleteById(String id) throws MedicalRecordException, DataNotFoundException {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Medical Record not found."));

        LocalDateTime lastUpdate = medicalRecord.getUpdatedAt();

        if (lastUpdate == null)
            lastUpdate = medicalRecord.getCreatedAt();

        if (medicalRecord.getPatientUid() == null && dateUtils.checkMedicalRecordValidity(lastUpdate.toLocalDate())) {
            medicalRecordRepository.deleteById(id);
        } else if (medicalRecord.getPatientUid() != null) {
            throw new DataIntegrityViolationException("This medical record is linked to a patient. It cannot be deleted.");
        } else {
            throw new MedicalRecordException(
                    "Unable to delete medical record. The minimum retention time for medical record is 10 years from the last entry date");
        }
    }
    
}
