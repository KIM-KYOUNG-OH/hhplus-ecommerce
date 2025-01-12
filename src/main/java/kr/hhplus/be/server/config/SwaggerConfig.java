package kr.hhplus.be.server.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("ecommerce")
                .packagesToScan("kr.hhplus.be.server.api.controller")
                .build();
    }
}
