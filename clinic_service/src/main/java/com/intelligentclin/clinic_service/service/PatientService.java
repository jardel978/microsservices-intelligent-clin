package com.intelligentclin.clinic_service.service;

import com.intelligentclin.clinic_service.model.dtos.converters.PatientModelMapperConverter;
import com.intelligentclin.common_models.models.dtos.patient.PatientReq;
import com.intelligentclin.common_models.models.dtos.patient.PatientResp;
import com.intelligentclin.clinic_service.model.entity.Patient;
import com.intelligentclin.clinic_service.repository.IPatientRepository;
import com.intelligentclin.clinic_service.service.exception.DataNotFoundException;
import com.intelligentclin.clinic_service.service.exception.RelatedEntityException;
import com.intelligentclin.clinic_service.service.utils.UtilDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private PatientModelMapperConverter patientConverter;

    @Autowired
    private UtilDate utilDate;

    public void save(PatientReq patientDTO) {
        Patient patient = patientConverter.mapModelReqToEntity(patientDTO, Patient.class);
        patient.setCreatedAt(LocalDateTime.now());
        patientRepository.save(patient);
    }

    public Optional<PatientResp> findById(String id) throws DataNotFoundException {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Patient not found."));

        patient.setAge(utilDate.generate(patient.getBirthDate(), LocalDate.now()));
        return Optional.ofNullable(patientConverter.mapEntityToModelResp(patient, PatientResp.class));
    }

    public Page<PatientResp> findCustom(
            String id,
            String firstName,
            String lastName,
            String cpf,
            Pageable pageable) {
        Page<Patient> page = patientRepository.findByIdOrFirstNameOrLastNameOrCpf(id, firstName, lastName, cpf, pageable);
        // Page<Patient> patientPage = new PageImpl<>(list, pageable, list.size());

        return page.map(patient -> {
            patient.setAge(utilDate.generate(patient.getBirthDate(), LocalDate.now()));
            return patientConverter.mapEntityToModelResp(patient, PatientResp.class);
        });
    }

    public Page<PatientResp> findAll(Pageable pageable) {
        Page<Patient> page = patientRepository.findAll(pageable);

        return page.map(patient -> {
            patient.setAge(utilDate.generate(patient.getBirthDate(), LocalDate.now()));
            PatientResp dto = patientConverter.mapEntityToModelResp(patient, PatientResp.class);
            return dto;
        });
    }

    public void deleteById(String id) throws RelatedEntityException, DataNotFoundException {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Patient not found in the database."));

        boolean hasMedicalRecord = patient.getMedicalRecordUid() != null;
        String baseMessage = "For data security reasons, it's not possible to delete patient: "
                + patient.getFirstName() + " " + patient.getLastName() + " because they are linked to ";

        if (hasMedicalRecord)
            throw new RelatedEntityException(baseMessage + "a medical record.");
        
        patientRepository.deleteById(patient.getUid());
    }

    public void update(String id, PatientReq patientDTO) {
        Patient patientFromDB = patientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found."));

        patientFromDB.setFirstName(patientDTO.getFirstName());
        patientFromDB.setLastName(patientDTO.getFirstName());
        patientFromDB.setCpf(patientDTO.getCpf());
        patientFromDB.setEmail(patientDTO.getEmail());
        patientFromDB.setPhone(patientDTO.getPhone());
        patientFromDB.setBirthDate(patientDTO.getBirthDate());
        patientFromDB.setAddress(patientDTO.getAddress());
        patientFromDB.setGender(patientDTO.getGender());
        patientRepository.save(patientFromDB);
    }

}
