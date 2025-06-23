package com.intelligentclin.clinic_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.intelligentclin.common_models.models.dtos.consultation.ConsultationReq;
import com.intelligentclin.common_models.models.dtos.consultation.ConsultationResp;
import com.intelligentclin.clinic_service.model.response.ResponseHandler;
import com.intelligentclin.clinic_service.service.ConsultationService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/consultation")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private ResponseHandler responseHandler;

    @Transactional
    @PostMapping("/save")
    public Mono<ResponseEntity<Object>> save(@RequestBody @Valid ConsultationReq consultationReq) {
        consultationService.save(consultationReq);
        return Mono.just(responseHandler.build(null, HttpStatus.CREATED, "success"));
    }

    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<Object>> findById(@PathVariable("id") String id) {
        ConsultationResp consultation = consultationService.findById(id);
        return Mono.just(responseHandler.build(consultation, HttpStatus.OK, "success"));
    }

    @GetMapping("/find")
    public Mono<ResponseEntity<Object>> findAll(
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return Mono.just(responseHandler.build(consultationService.findAll(pageable), HttpStatus.OK, "success"));
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable("id") String id) {
        consultationService.deleteById(id);
        return Mono.just(responseHandler.build(null, HttpStatus.NO_CONTENT, "success"));
    }

    @Transactional
    @PutMapping("/update/{id}")
    // @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Mono<ResponseEntity<Object>> update(@PathVariable("id") String id, @RequestBody @Valid ConsultationReq consultationReq) {
        consultationService.update(id, consultationReq);
        return Mono.just(responseHandler.build(null, HttpStatus.NO_CONTENT, "success"));
    }

}
