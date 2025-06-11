package com.intelligentclin.clinic_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.intelligentclin.clinic_service.controller.exception.ConstraintException;
import com.intelligentclin.clinic_service.dtos.consultation.ConsultationReq;
import com.intelligentclin.clinic_service.dtos.consultation.ConsultationResp;
import com.intelligentclin.clinic_service.entity.response.ResponseHandler;
import com.intelligentclin.clinic_service.service.ConsultationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clinic/consultation")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private ResponseHandler responseHandler;

    @Transactional
    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Valid ConsultationReq consultationReq, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
        consultationService.save(consultationReq);
        return responseHandler.build(null, HttpStatus.CREATED, "consultation saved.");
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") String id) {
        ConsultationResp consultation = consultationService.findById(id);
        return responseHandler.build(consultation, HttpStatus.OK, "consultation found.");
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findAll(Pageable pageable) {
        return responseHandler.build(consultationService.findAll(pageable), HttpStatus.OK, "consultations found.");
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable("id") String id) {
        consultationService.deleteById(id);
        return responseHandler.build(null, HttpStatus.NO_CONTENT, "consultation deleted.");
    }

    @Transactional
    @PutMapping("/update/{id}")
    // @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody @Valid ConsultationReq consultationReq, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
        consultationService.update(id, consultationReq);
        return responseHandler.build(null, HttpStatus.NO_CONTENT, "consultation updated");
    }

}
