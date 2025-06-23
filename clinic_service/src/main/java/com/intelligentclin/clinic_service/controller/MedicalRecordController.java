package com.intelligentclin.clinic_service.controller;

import com.intelligentclin.common_models.models.dtos.medicalRecord.MedicalRecordReq;
import com.intelligentclin.common_models.models.dtos.medicalRecord.MedicalRecordResp;
import com.intelligentclin.clinic_service.model.response.ResponseHandler;
import com.intelligentclin.clinic_service.service.MedicalRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ResponseHandler responseHandler;

    @Transactional
    @PostMapping("/save")
    public Mono<ResponseEntity<Object>> save(
            @RequestBody @Valid MedicalRecordReq medicalRecordReq) {
        medicalRecordService.save(medicalRecordReq);
        return Mono.just(responseHandler.build(null, HttpStatus.CREATED, "success"));
    }

    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<Object>> findById(@PathVariable("id") String id) {
        MedicalRecordResp medicalRecord = medicalRecordService.findById(id).get();
        return Mono.just(responseHandler.build(medicalRecord, HttpStatus.OK, "success"));
    }

    @GetMapping("/find")
    public Mono<ResponseEntity<Object>> findAll(
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MedicalRecordResp> pageResult = medicalRecordService.findAll(pageable);
        return Mono.just(responseHandler.build(pageResult, HttpStatus.OK, "success"));
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable("id") String id) {
        medicalRecordService.deleteById(id);
        return Mono.just(responseHandler.build(null, HttpStatus.NO_CONTENT, "success"));
    }

    @PutMapping("/update")
    @Transactional
    public Mono<ResponseEntity<Object>> update(@RequestBody @Valid MedicalRecordReq medicalRecordReq) {
        medicalRecordService.save(medicalRecordReq);
        return Mono.just(responseHandler.build(null, HttpStatus.NO_CONTENT, "success"));
    }
}
