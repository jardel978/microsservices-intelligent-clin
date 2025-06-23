package com.intelligentclin.users_service.controller;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intelligentclin.users_service.model.dto.AuthRequest;
import com.intelligentclin.users_service.model.dto.RefreshTokenRequest;
import com.intelligentclin.users_service.model.dto.TokenResult;
import com.intelligentclin.users_service.model.response.ResponseHandler;
import com.intelligentclin.users_service.service.UserService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;
    @Autowired
    private UserService userService;    
    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        RSAKey key = new RSAKey.Builder(publicKey).keyID("users-service-key").build();
        return new JWKSet(key).toJSONObject();
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> login(@Valid @RequestBody AuthRequest authRequest) {
        TokenResult tokens = userService.login(authRequest);
        return Mono.just(responseHandler.build(tokens, HttpStatus.OK, "success"));
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<Object>> refresh(@RequestBody RefreshTokenRequest request) {
        TokenResult tokens = userService.refreshToken(request.refreshToken());
        return Mono.just(responseHandler.build(tokens, HttpStatus.OK, "success"));
    }
   
}