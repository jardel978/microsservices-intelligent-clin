package com.intelligentclin.clinic_service.controller;

import com.intelligentclin.clinic_service.controller.exception.ConstraintException;
import com.intelligentclin.common_models.models.dtos.patient.PatientReq;
import com.intelligentclin.common_models.models.dtos.patient.PatientResp;
import com.intelligentclin.clinic_service.model.response.ResponseHandler;
import com.intelligentclin.clinic_service.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private ResponseHandler responseHandler;

    @Transactional
    @PostMapping("/save")
    public Mono<ResponseEntity<Object>> save(@Valid @RequestBody PatientReq patientReq) {
        patientService.save(patientReq);
        return Mono.just(responseHandler.build(null, HttpStatus.CREATED, "success"));
    }

    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<Object>> findById(@PathVariable("id") String id) {
        PatientResp patient = patientService.findById(id).get();
        return Mono.just(responseHandler.build(patient, HttpStatus.OK, "success"));
    }

    @GetMapping("/find-custom")
    public Mono<ResponseEntity<Object>> findCustom(@RequestParam(value = "id", required = false) String id,
                                                @RequestParam(value = "first-name", required = false) String firstName,
                                                @RequestParam(value = "lastName", required = false) String lastName,
                                                @RequestParam(value = "cpf", required = false) String cpf,
                                                
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PatientResp> pageResult = patientService.findCustom(id, firstName, lastName, cpf, pageable);
        return Mono.just(responseHandler.build(pageResult, HttpStatus.OK, "success"));
    }

    @GetMapping("/permitAll/todos")
    public Mono<ResponseEntity<Object>> findAll(
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PatientResp> pageResult = patientService.findAll(pageable);
        return Mono.just(responseHandler.build(pageResult, HttpStatus.OK, "success"));
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable("id") String id) {
        patientService.deleteById(id);
        return Mono.just(responseHandler.build(null, HttpStatus.NO_CONTENT, "success"));
    }

    @Transactional
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Object>> update(@PathVariable("id") String id, @Valid @RequestBody PatientReq patientReq,
                                       BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        patientService.update(id, patientReq);
        return Mono.just(responseHandler.build(null, HttpStatus.NO_CONTENT, "success"));
    }

}

