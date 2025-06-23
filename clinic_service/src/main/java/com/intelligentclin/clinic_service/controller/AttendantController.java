package com.intelligentclin.clinic_service.controller;

import com.intelligentclin.common_models.models.dtos.attendat.AttendantReq;
import com.intelligentclin.common_models.models.dtos.attendat.AttendantResp;
import com.intelligentclin.clinic_service.model.response.ResponseHandler;
import com.intelligentclin.clinic_service.service.AttendantService;

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
@RequestMapping("/attendant")
public class AttendantController {

    @Autowired
    private AttendantService attendantService;

    @Autowired
    private ResponseHandler responseHandler;

    @Transactional
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('SCOPE_clinic.write')")
    public Mono<ResponseEntity<Object>> save(@Valid @RequestBody AttendantReq attendantReq) {
        attendantService.save(attendantReq);
        return  Mono.just(responseHandler.build(null, HttpStatus.CREATED, "success"));
    }

    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<Object>> findById(@PathVariable("id") String id) {
        AttendantResp attendant = attendantService.findById(id).get();
        return  Mono.just(responseHandler.build(attendant, HttpStatus.OK, "success"));
    }

    @GetMapping("/find-custom")
    public Mono<ResponseEntity<Object>> findCustom(@RequestParam(value = "id", required = false) String id,
                                                @RequestParam(value = "first-name", required = false) String firstName,
                                                @RequestParam(value = "last-name", required = false) String lastName,
                                                @RequestParam(value = "cpf", required = false) String cpf,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendantResp> pageResult = attendantService.findCustom(id, firstName, lastName, cpf, pageable);
        return  Mono.just(responseHandler.build(pageResult, HttpStatus.OK, "success"));
    }

    @GetMapping("/find")
    public Mono<ResponseEntity<Object>> findAll(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendantResp> pageResult = attendantService.findAll(pageable);
        return  Mono.just(responseHandler.build(pageResult, HttpStatus.OK, "success"));
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public Mono<ResponseEntity<Object>> deleteById(@PathVariable("id") String id) {
        attendantService.deleteById(id);
        return Mono.just(responseHandler.build(null, HttpStatus.NO_CONTENT, "success"));
    }

    @PutMapping("/update/{id}")
    @Transactional
    public Mono<ResponseEntity<Object>> update(@PathVariable("id") String id, @Valid @RequestBody AttendantReq attendantReq) {
        attendantService.update(id, attendantReq);
        return Mono.just(responseHandler.build(null, HttpStatus.NO_CONTENT, "success"));
    }

}
