package com.valentinakole.lms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Lms System Api")
                                .version("1.0.3 from 03 Oct 2023")
                                .contact(
                                        new Contact()
                                                .email("valentinavasileva34@gmail.com")
                                                .url("https://github.com/Valentina810")
                                                .name("Valentina Kolesnikova | Roman Yudin")
                                )
                );
    }
}
