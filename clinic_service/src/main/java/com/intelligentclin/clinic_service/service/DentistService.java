package com.intelligentclin.clinic_service.service;

import com.intelligentclin.clinic_service.model.dtos.converters.DentistModelMapperConverter;
import com.intelligentclin.common_models.models.dtos.dentist.DentistReq;
import com.intelligentclin.common_models.models.dtos.dentist.DentistResp;
import com.intelligentclin.clinic_service.model.entity.Dentist;
import com.intelligentclin.common_models.models.enums.Specialty;
import com.intelligentclin.clinic_service.repository.IDentistRepository;
import com.intelligentclin.clinic_service.service.exception.DataNotFoundException;
import com.intelligentclin.clinic_service.service.exception.RelatedEntityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DentistService {

    @Autowired
    private IDentistRepository dentistRepository;

    @Autowired
    private DentistModelMapperConverter dentistConverter;

    // Save Dentist
    public void save(DentistReq dentistDTO) {
        Dentist dentist = dentistConverter.mapModelReqToEntity(dentistDTO, Dentist.class);
        dentistRepository.save(dentist);
    }

    // Find Dentist by ID
    public Optional<DentistResp> findById(String id) throws DataNotFoundException {
        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Dentist not found."));
        return Optional.ofNullable(dentistConverter.mapEntityToModelResp(dentist, DentistResp.class));
    }

    // Custom search with filtering
    public Page<DentistResp> findCustom(
            String id,
            String firstName,
            String lastName,
            String cpf,
            Pageable pageable) {
        Page<Dentist> page = dentistRepository.findByIdOrFirstNameOrLastNameOrCpf(id, firstName, lastName, cpf, 
                pageable);
        return page.map(dentist -> dentistConverter.mapEntityToModelResp(dentist, DentistResp.class));
    }

    // Find by registration number (matricula)
    public DentistResp findByRegistrationNumber(String registrationNumber) throws DataNotFoundException {
        Dentist dentist = dentistRepository.findByRegistrationNumberIgnoreCaseContaining(registrationNumber)
                .orElseThrow(
                        () -> new DataNotFoundException("No dentist found with the provided registration number."));
        return dentistConverter.mapEntityToModelResp(dentist, DentistResp.class);
    }

    // Find by specialty with pagination
    public Page<DentistResp> findBySpecialty(Specialty specialty, Pageable pageable) {
        Page<Dentist> dentistsPage = dentistRepository.findBySpecialtiesContaining(specialty, pageable);
        return dentistsPage.map(dentist -> {
            DentistResp dentistDTO = dentistConverter.mapEntityToModelResp(dentist, DentistResp.class);
            return dentistDTO;
        });
    }

    // Find all dentists paginated
    public Page<DentistResp> findAll(Pageable pageable) {
        Page<Dentist> dentistsPage = dentistRepository.findAll(pageable);
        return dentistsPage.map(dentist -> {
            DentistResp dentistDTO = dentistConverter.mapEntityToModelResp(dentist, DentistResp.class);
            return dentistDTO;
        });
    }

    // Delete Dentist by ID
    public void deleteById(String id) throws DataNotFoundException, RelatedEntityException {
        Dentist dentist = dentistRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Dentist not found."));

        boolean hasAppointments = !dentist.getAppointmentUids().isEmpty();
        boolean hasMedicalRecords = !dentist.getRecordUids().isEmpty();

        if (hasMedicalRecords && hasAppointments) {
            throw new RelatedEntityException("Dentist " + dentist.getFirstName() + " " + dentist.getLastName()
                    + " has appointments and medical records. Cannot delete.");
        }
        if (hasAppointments) {
            throw new RelatedEntityException(
                    "Cannot delete dentist " + dentist.getFirstName() + " " + dentist.getLastName()
                            + " because they are linked to one or more appointments.");
        }
        if (hasMedicalRecords) {
            throw new RelatedEntityException(
                    "Cannot delete dentist " + dentist.getFirstName() + " " + dentist.getLastName()
                            + " because they are linked to one or more medical records.");
        }
        dentistRepository.deleteById(dentist.getUid());
    }

    // Update Dentist by ID
    public void update(String uid, DentistReq dentistDTO) throws DataNotFoundException {
        Dentist existingDentist = dentistRepository.findById(uid)
                .orElseThrow(() -> new DataNotFoundException("Dentist not found."));
        
        existingDentist.setFirstName(dentistDTO.getFirstName());
        existingDentist.setLastName(dentistDTO.getFirstName());
        existingDentist.setCpf(dentistDTO.getCpf());
        existingDentist.setEmail(dentistDTO.getEmail());
        existingDentist.setPhone(dentistDTO.getPhone());
        existingDentist.setRegistrationNumber(dentistDTO.getRegistrationNumber());
        existingDentist.setSpecialties(dentistDTO.getSpecialties());
        dentistRepository.save(existingDentist);
    }

    // Check if Dentist has appointments
    public Boolean checkIfDentistHasAppointments(Dentist dentist) {
        return !dentist.getAppointmentUids().isEmpty();
    }
}
