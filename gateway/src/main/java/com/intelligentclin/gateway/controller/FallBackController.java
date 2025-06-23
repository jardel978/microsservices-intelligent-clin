package com.intelligentclin.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping ("/fallback")
public class FallBackController {

	@GetMapping ("/clinic")
	public Mono<ResponseEntity<String>> moviesFallback () {
		return Mono.just(new ResponseEntity<>("Servidor de dados médicos indisponível. Por favor, contate o suporte.",
				HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@GetMapping ("/users")
	public Mono<ResponseEntity<String>> catalogFallback () {
		return Mono.just(new ResponseEntity<>("Servidor de usuários indisponível. Por favor, contate o suporte.",
				HttpStatus.INTERNAL_SERVER_ERROR));
	}
}