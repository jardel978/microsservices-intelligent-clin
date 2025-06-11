package com.intelligentclin.users_service.controller;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.intelligentclin.users_service.model.dto.AuthRequest;
import com.intelligentclin.users_service.model.dto.CreateUserDto;
import com.intelligentclin.users_service.model.dto.RefreshTokenRequest;
import com.intelligentclin.users_service.model.dto.TokenResult;
import com.intelligentclin.users_service.model.entity.User;
import com.intelligentclin.users_service.model.response.ResponseHandler;
import com.intelligentclin.users_service.service.IUserService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController("/users")
public class UserController {

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;
    private IUserService userService;    
    private ResponseHandler responseHandler;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<Object> newUser(@Valid @RequestBody CreateUserDto dto, BindingResult bgresult) {
        if (bgresult.hasErrors())
            throw new RuntimeException(bgresult.getAllErrors().get(0).getDefaultMessage());
        userService.newUser(dto);
        return responseHandler.build(null, HttpStatus.OK, "created.");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody AuthRequest authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        TokenResult tokens = userService.login(authRequest);
        return responseHandler.build(tokens, HttpStatus.OK, "Login successful");
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody RefreshTokenRequest request) {
        TokenResult tokens = userService.refreshToken(request.refreshToken());
        return responseHandler.build(tokens, HttpStatus.OK, "Login successful");
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Object> findAll(Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return responseHandler.build(users, HttpStatus.OK, "created.");
    }
}