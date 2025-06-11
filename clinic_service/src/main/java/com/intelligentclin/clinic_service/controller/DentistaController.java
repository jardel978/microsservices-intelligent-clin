package com.intelligentclin.clinic_service.controller;

import com.intelligentclin.clinic_service.controller.exception.ConstraintException;
import com.intelligentclin.clinic_service.dtos.dentist.DentistReq;
import com.intelligentclin.clinic_service.dtos.dentist.DentistResp;
import com.intelligentclin.clinic_service.entity.enums.Specialty;
import com.intelligentclin.clinic_service.entity.response.ResponseHandler;
import com.intelligentclin.clinic_service.service.DentistService;
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
@RequestMapping("/clinic/dentist")
public class DentistaController {

    @Autowired
    private DentistService dentistService;

    @Autowired
    private ResponseHandler responseHandler;

    @Transactional
    @PostMapping("/save")
    public ResponseEntity<Object> save(@Valid @RequestBody DentistReq dentistReq, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        dentistService.save(dentistReq);
        return responseHandler.build(null, HttpStatus.CREATED, "success");
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") String id) {
        DentistResp dentist = dentistService.findById(id).get();
        return responseHandler.build(dentist, HttpStatus.OK, "success");
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findCustom(Pageable pageable,
                                                @RequestParam(value = "id", required = false) String id,
                                                @RequestParam(value = "first-name", required = false) String firstName,
                                                @RequestParam(value = "last-name", required = false) String lastName,
                                                @RequestParam(value = "cpf", required = false) String cpf) {
        Page<DentistResp> page = dentistService.findCustom(pageable, id, firstName, lastName, cpf);
        return responseHandler.build(page, HttpStatus.OK, "Dentists found.");
    }

    @GetMapping("/find-registration/{registration-number}")
    public ResponseEntity<Object> findByRegistrationNumber(@PathVariable("registration-number") String registrationNumber) {
        DentistResp dentist = dentistService.findByRegistrationNumber(registrationNumber);
        return responseHandler.build(dentist, HttpStatus.OK, "success");
    }

    @GetMapping("/find-specialties/{specialties}")
    public ResponseEntity<Object> findBySpecialty(
            Pageable pageable,
            @RequestParam(value = "specialties") String specialties) {
        Specialty stringParaEnumEspecialidade = Specialty.valueOf(specialties.toUpperCase());
            Page<DentistResp> page = dentistService.findBySpecialty(pageable,
                    stringParaEnumEspecialidade);
            return responseHandler.build(page, HttpStatus.OK, "success");
    }

    @GetMapping("/find")
    public ResponseEntity<Object> findAll(Pageable pageable) {
        Page<DentistResp> page = dentistService.findAll(pageable);
        return responseHandler.build(page, HttpStatus.OK, "success");
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> excluirPorId(@PathVariable("id") String id) {
        dentistService.deleteById(id);
        return responseHandler.build(null, HttpStatus.NO_CONTENT, "success");
    }

    @Transactional
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(
            @PathVariable("id") String id,
            @Valid @RequestBody DentistReq dentistReq, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new ConstraintException(bgresult.getAllErrors().get(0).getDefaultMessage());

        dentistService.update(id, dentistReq);
        return responseHandler.build(null, HttpStatus.NO_CONTENT, "success");
    }
}
