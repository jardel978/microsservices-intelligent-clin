package com.intelligentclin.clinic_service.controller;

import com.intelligentclin.clinic_service.controller.exception.ConstraintException;
import com.intelligentclin.clinic_service.dtos.medicalRecord.MedicalRecordReq;
import com.intelligentclin.clinic_service.dtos.medicalRecord.MedicalRecordResp;
import com.intelligentclin.clinic_service.entity.response.ResponseHandler;
import com.intelligentclin.clinic_service.service.MedicalRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clinic/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ResponseHandler responseHandler;

    @Transactional
    @PostMapping("/save")
    public ResponseEntity<Object> save(
            @RequestBody @Valid MedicalRecordReq medicalRecordReq, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        medicalRecordService.save(medicalRecordReq);
        return responseHandler.build(null, HttpStatus.CREATED, "success");
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") String id) {
        MedicalRecordResp medicalRecord = medicalRecordService.findById(id).get();
        return responseHandler.build(medicalRecord, HttpStatus.OK, "success");
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findAll(Pageable pageable) {
        Page<MedicalRecordResp> page = medicalRecordService.findAll(pageable);
        return responseHandler.build(page, HttpStatus.OK, "success");
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String id) {
        medicalRecordService.deleteById(id);
        return responseHandler.build(null, HttpStatus.NO_CONTENT, "success");
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<Object> update(@RequestBody @Valid MedicalRecordReq medicalRecordReq, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        medicalRecordService.save(medicalRecordReq);
        return responseHandler.build(null, HttpStatus.NO_CONTENT, "success");
    }
}
