package com.tej.smart_lms;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

@OpenAPIDefinition(
		info = @Info(
				title = "Secure API",
				version = "1.0",
				description = "API secured with Spring Security and documented with Swagger"
		),
		security = @SecurityRequirement(name = "basicAuth")
)
@SecurityScheme(
		name = "basicAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "basic"
)

public class SmartLms {

	public static void main(String[] args) {
//		System.out.println("Starting spring boot application");
		SpringApplication.run(SmartLms.class, args);
	}

}
