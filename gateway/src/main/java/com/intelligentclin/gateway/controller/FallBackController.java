package com.intelligentclin.gateway.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/fallback")
public class FallBackController {

	@GetMapping ("/clinic")
	public ResponseEntity<String> moviesFallback () {
		return new ResponseEntity<>("Servidor de dados médicos indisponível. Por favor, contate o suporte.",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping ("/users")
	public ResponseEntity<String> catalogFallback () {
		return new ResponseEntity<>("Servidor de usuários indisponível. Por favor, contate o suporte.",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}