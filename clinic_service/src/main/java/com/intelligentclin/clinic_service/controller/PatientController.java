package com.intelligentclin.clinic_service.controller;

import com.intelligentclin.clinic_service.controller.exception.ConstraintException;
import com.intelligentclin.clinic_service.dtos.patient.PatientReq;
import com.intelligentclin.clinic_service.dtos.patient.PatientResp;
import com.intelligentclin.clinic_service.entity.response.ResponseHandler;
import com.intelligentclin.clinic_service.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController()
@RequestMapping("/clinic/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private ResponseHandler responseHandler;

    @Transactional
    @PostMapping("/save")
    public ResponseEntity<Object> save(@Valid @RequestBody PatientReq patientReq, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
        patientService.save(patientReq);
        return responseHandler.build(null, HttpStatus.CREATED, "success");
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") String id) {
        PatientResp patient = patientService.findById(id).get();
        return responseHandler.build(patient, HttpStatus.OK, "success");
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findCustom(Pageable pageable,
                                                @RequestParam(value = "id", required = false) String id,
                                                @RequestParam(value = "first-name", required = false) String firstName,
                                                @RequestParam(value = "lastName", required = false) String lastName,
                                                @RequestParam(value = "cpf", required = false) String cpf) {
        Page<PatientResp> page = patientService.findCustom(pageable, id, firstName, lastName, cpf);
        return responseHandler.build(page, HttpStatus.OK, "success");
    }

    @GetMapping("/permitAll/todos")
    public ResponseEntity<Object> findAll(Pageable pageable) {
        Page<PatientResp> page = patientService.findAll(pageable);
        return responseHandler.build(page, HttpStatus.OK, "success");
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String id) {
        patientService.deleteById(id);
        return responseHandler.build(null, HttpStatus.NO_CONTENT, "success");
    }

    @Transactional
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @Valid @RequestBody PatientReq patientReq,
                                       BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        patientService.update(id, patientReq);
        return responseHandler.build(null, HttpStatus.NO_CONTENT, "success");
    }

}

