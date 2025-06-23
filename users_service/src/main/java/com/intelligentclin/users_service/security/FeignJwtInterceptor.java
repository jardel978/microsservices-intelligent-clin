package com.intelligentclin.users_service.security;

import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class FeignJwtInterceptor {

    @Autowired
    private InternalJwtTokenGenerator tokenGenerator;

    @Bean
    public RequestInterceptor internalRequestInterceptor() {
        return template -> {
            String token = tokenGenerator.generateTokenForInternalCommunication();
            template.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        };
    }
}
