package com.example.mySpringApi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Configuration for the OpenAPI documentation.
 *
 * This configuration includes details about the API such as contact, version, license, and servers.
 * Additionally, it provides security scheme details for JWT authentication.
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Zach",
                        email = "zach@example.com",
                        url = "https://google.com"
                ),
                description = "OpenApi documentation",
                title = "OpenApi specification - Zach",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://localhost:8080"
                ),
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
