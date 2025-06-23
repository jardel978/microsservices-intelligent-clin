package com.intelligentclin.clinic_service.controller;

import com.intelligentclin.common_models.models.dtos.dentist.DentistReq;
import com.intelligentclin.common_models.models.dtos.dentist.DentistResp;
import com.intelligentclin.common_models.models.enums.Specialty;
import com.intelligentclin.clinic_service.model.response.ResponseHandler;
import com.intelligentclin.clinic_service.service.DentistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/dentist")
public class DentistController {

    @Autowired
    private DentistService dentistService;

    @Autowired
    private ResponseHandler responseHandler;

    @Transactional
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('SCOPE_clinic.write')")
    public Mono<ResponseEntity<Object>> save(@Valid @RequestBody DentistReq dentistReq) {
        dentistService.save(dentistReq);
        return Mono.just(responseHandler.build(null, HttpStatus.CREATED, "success"));
    }

    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<Object>> findById(@PathVariable("id") String id) {
        DentistResp dentist = dentistService.findById(id).get();
        return Mono.just(responseHandler.build(dentist, HttpStatus.OK, "success"));
    }

    @GetMapping("/find-custom")
    public Mono<ResponseEntity<Object>> findCustom(@RequestParam(value = "id", required = false) String id,
                                                @RequestParam(value = "first-name", required = false) String firstName,
                                                @RequestParam(value = "last-name", required = false) String lastName,
                                                @RequestParam(value = "cpf", required = false) String cpf,
                                                
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DentistResp> pageResult = dentistService.findCustom(id, firstName, lastName, cpf, pageable);
        return Mono.just(responseHandler.build(pageResult, HttpStatus.OK, "success"));
    }

    @GetMapping("/find-registration/{registration-number}")
    public Mono<ResponseEntity<Object>> findByRegistrationNumber(@PathVariable("registration-number") String registrationNumber) {
        DentistResp dentist = dentistService.findByRegistrationNumber(registrationNumber);
        return Mono.just(responseHandler.build(dentist, HttpStatus.OK, "success"));
    }

    @GetMapping("/find-specialties/{specialties}")
    public Mono<ResponseEntity<Object>> findBySpecialty(
                        @RequestParam(value = "specialties") String specialties,
                        
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specialty stringParaEnumEspecialidade = Specialty.valueOf(specialties.toUpperCase());
            Page<DentistResp> pageResult = dentistService.findBySpecialty(stringParaEnumEspecialidade, pageable);
            return Mono.just(responseHandler.build(pageResult, HttpStatus.OK, "success"));
    }

    @GetMapping("/find")
    public Mono<ResponseEntity<Object>> findAll(
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DentistResp> pageResult = dentistService.findAll(pageable);
        return Mono.just(responseHandler.build(pageResult, HttpStatus.OK, "success"));
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Object>> excluirPorId(@PathVariable("id") String id) {
        dentistService.deleteById(id);
        return Mono.just(responseHandler.build(null, HttpStatus.NO_CONTENT, "success"));
    }

    @Transactional
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Object>> update(
            @PathVariable("id") String id,
            @Valid @RequestBody DentistReq dentistReq) {
        dentistService.update(id, dentistReq);
        return Mono.just(responseHandler.build(null, HttpStatus.NO_CONTENT, "success"));
    }
}
