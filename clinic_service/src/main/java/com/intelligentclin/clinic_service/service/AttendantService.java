package com.intelligentclin.clinic_service.service;

import com.intelligentclin.common_models.models.dtos.attendat.AttendantReq;
import com.intelligentclin.common_models.models.dtos.attendat.AttendantResp;
import com.intelligentclin.clinic_service.model.dtos.converters.AttendantModelMapperConverter;
import com.intelligentclin.clinic_service.model.entity.Attendant;
import com.intelligentclin.clinic_service.repository.IAttendantRepository;
import com.intelligentclin.clinic_service.service.exception.DataNotFoundException;
import com.intelligentclin.clinic_service.service.exception.RelatedEntityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AttendantService {

    @Autowired
    private IAttendantRepository attendantRepository;

    @Autowired
    private AttendantModelMapperConverter attendantConverter;

    public void save(AttendantReq attendantReq) {
        Attendant Attendant = attendantConverter.mapModelReqToEntity(attendantReq, Attendant.class);
        attendantRepository.save(Attendant);
    }

    public Optional<AttendantResp> findById(String id) throws DataNotFoundException {
        Attendant attendant = attendantRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Attendant not found."));
        return Optional.ofNullable(attendantConverter.mapEntityToModelResp(attendant, AttendantResp.class));
    }

    public Page<AttendantResp> findCustom(
            String id,
            String firstName,
            String lastName,
            String cpf,
            Pageable pageable) {
        Page<Attendant> page = attendantRepository.findByIdOrFirstNameOrLastNameOrCpf(id, firstName, lastName, cpf, pageable);
        // Page<Attendant> AttendantPage = new PageImpl<>(list, list.size(),         );

        return page.map(attendant -> {
            return attendantConverter.mapEntityToModelResp(attendant, AttendantResp.class);
        });
    }

    public Page<AttendantResp> findAll(Pageable pageable) {
        Page<Attendant> page = attendantRepository.findAll(pageable);

        return page.map(attendant -> {
            AttendantResp dto = attendantConverter.mapEntityToModelResp(attendant, AttendantResp.class);
            return dto;
        });
    }

    public void deleteById(String id) throws RelatedEntityException, DataNotFoundException {
        Attendant attendant = attendantRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Attendant not found in the database."));

        attendantRepository.deleteById(attendant.getUid());
    }

    public void update(String id, AttendantReq attendantReq) {
        Attendant attendantFromDB = attendantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Attendant not found."));

        attendantFromDB.setFirstName(attendantReq.getFirstName());
        attendantFromDB.setLastName(attendantReq.getFirstName());
        attendantFromDB.setCpf(attendantReq.getCpf());
        attendantFromDB.setEmail(attendantReq.getEmail());
        attendantRepository.save(attendantFromDB);
    }

}
