package com.intelligentclin.clinic_service.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        byte[] bytes = "Invalid token.".getBytes();
        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(bytes)));
    }
}
