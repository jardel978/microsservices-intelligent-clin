package com.intelligentclin.users_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intelligentclin.users_service.model.dto.CreateUserDto;
import com.intelligentclin.users_service.model.entity.User;
import com.intelligentclin.users_service.model.response.ResponseHandler;
import com.intelligentclin.users_service.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;    
    @Autowired
    private ResponseHandler responseHandler;

    @Transactional
    @PostMapping("/create")
    public Mono<ResponseEntity<Object>> newUser(@Valid @RequestBody CreateUserDto dto) {
        userService.newUser(dto);
        return Mono.just(responseHandler.build(null, HttpStatus.OK, "success"));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public Mono<ResponseEntity<Object>> findAll(Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return Mono.just(responseHandler.build(users, HttpStatus.OK, "success"));
    }
}