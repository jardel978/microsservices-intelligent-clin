package com.intelligentclin.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Flux;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

        @Bean
        public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
                return http
                                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                                .authorizeExchange(exchange -> exchange
                                                .pathMatchers(HttpMethod.POST, "/actuator/**").permitAll()
                                                .pathMatchers(HttpMethod.POST, "/users/create").permitAll()
                                                .pathMatchers(HttpMethod.POST, "/users/me").permitAll()
                                                .pathMatchers(HttpMethod.POST, "/users/auth/login").permitAll()
                                                .pathMatchers(HttpMethod.POST, "/users/auth/refresh").permitAll()
                                                .anyExchange().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .jwt(jwt -> jwt.jwtAuthenticationConverter(
                                                                grantedAuthoritiesExtractor())))
                                .build();
        }

        @Bean
        public ReactiveJwtAuthenticationConverter grantedAuthoritiesExtractor() {
                JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
                authoritiesConverter.setAuthorityPrefix("SCOPE_");
                authoritiesConverter.setAuthoritiesClaimName("scope");

                ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
                converter.setJwtGrantedAuthoritiesConverter(jwt ->
                        Flux.fromIterable(authoritiesConverter.convert(jwt)) // CORREÇÃO AQUI
                );

                return converter;
        }
}
