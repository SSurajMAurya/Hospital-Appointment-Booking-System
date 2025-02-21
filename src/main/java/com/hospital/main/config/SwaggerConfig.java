package com.hospital.main.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Hospital Booking API",
        version = "1.0",
        description = "API documentation for Hospital Booking Application",
        contact = @Contact(name = "Suraj", url = "example.com", email = "suraj@example.com"),
        license = @License(name = "License of API", url = "API license URL")
    )
)
public class SwaggerConfig {

    

}
