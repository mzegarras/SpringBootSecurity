package com.hascode.tutorial;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableOAuth2Resource
public class SampleResourceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SampleResourceApplication.class, args);
	}

	@RequestMapping("/")
	public String securedCall() {
		return "success (id: " + UUID.randomUUID().toString().toUpperCase() + ")";
	}
}
