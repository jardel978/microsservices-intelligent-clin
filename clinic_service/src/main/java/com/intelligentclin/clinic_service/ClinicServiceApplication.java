package com.intelligentclin.clinic_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.intelligentclin.clinic_service.model.response.ResponseHandler;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
// @EnableRabbit
public class ClinicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicServiceApplication.class, args);
	}

	@Bean
    public ResponseHandler responseHandler() {
        return new ResponseHandler();
    }

}
