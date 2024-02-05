package com.seek.appcandidate;

import com.seek.appcandidate.application.port.input.IAuthService;
import com.seek.appcandidate.infrastructure.dto.RegisterDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
@EnableR2dbcRepositories
@OpenAPIDefinition(info = @Info(
		title = "App Candidate WebFlux",
		version = "1.0",
		description = "App Candidate WebFlux API",
		contact = @Contact(
				name = "Arturo Diaz",
				email = "diazartiagacarlosarturo@gmail.com"
		)
))
public class AppCandidateApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppCandidateApplication.class, args);
	}
}
