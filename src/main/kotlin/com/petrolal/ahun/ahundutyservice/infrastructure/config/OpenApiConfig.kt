package com.petrolal.ahun.ahundutyservice.infrastructure.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("Ahun Duty Service")
                .version("1.0")
                .description("Service to generate and maintain duty"))
    }
}