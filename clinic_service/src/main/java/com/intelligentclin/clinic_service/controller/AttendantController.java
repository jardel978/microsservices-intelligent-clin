package com.intelligentclin.clinic_service.controller;

import com.intelligentclin.clinic_service.controller.exception.ConstraintException;
import com.intelligentclin.clinic_service.dtos.attendat.AttendantReq;
import com.intelligentclin.clinic_service.dtos.attendat.AttendantResp;
import com.intelligentclin.clinic_service.entity.response.ResponseHandler;
import com.intelligentclin.clinic_service.service.AttendantService;
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
@RequestMapping("/clinic/attendant")
public class AttendantController {

    @Autowired
    private AttendantService attendantService;

    @Autowired
    private ResponseHandler responseHandler;

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<Object> save(@Valid @RequestBody AttendantReq attendantReq, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());
        attendantService.save(attendantReq);
        return  responseHandler.build(null, HttpStatus.CREATED, "success");
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") String id) {
        AttendantResp attendant = attendantService.findById(id).get();
        return  responseHandler.build(attendant, HttpStatus.OK, "success");
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findCustom(Pageable pageable,
                                                        @RequestParam(value = "id", required = false) String id,
                                                        @RequestParam(value = "first-name", required = false) String firstName,
                                                        @RequestParam(value = "last-name", required = false) String lastName,
                                                        @RequestParam(value = "cpf", required = false) String cpf) {
        Page<AttendantResp> page = attendantService.findCustom(pageable, id, firstName, lastName, cpf);
        return  responseHandler.build(page, HttpStatus.OK, "success");
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findAll(Pageable pageable) {
        Page<AttendantResp> page = attendantService.findAll(pageable);
        return  responseHandler.build(page, HttpStatus.OK, "success");
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Object> deleteById(@PathVariable("id") String id) {
        attendantService.deleteById(id);
        return responseHandler.build(null, HttpStatus.NO_CONTENT, "success");
    }

    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<Object> update(@PathVariable("id") String id, @Valid @RequestBody AttendantReq attendantReq,
                                       BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        attendantService.update(id, attendantReq);
        return responseHandler.build(null, HttpStatus.NO_CONTENT, "success");
    }

}
