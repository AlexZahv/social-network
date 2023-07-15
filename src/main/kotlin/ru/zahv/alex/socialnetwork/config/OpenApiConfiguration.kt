package ru.zahv.alex.socialnetwork.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(info = Info(title = "Social-network", version = "0.1"))
@SecurityScheme(name = OpenApiConfiguration.BEARER_AUTH_SECURITY_SCHEME_NAME, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
class OpenApiConfiguration {
    companion object {
        const val BEARER_AUTH_SECURITY_SCHEME_NAME = "bearerAuth"
    }
}
