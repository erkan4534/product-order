package com.microservices.inventory.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI inventoryServiceAPI(){
        return new OpenAPI()
                .info(new Info().title("Inventory Service API")
                                .description("API documentation for the Inventory Service")
                                .version("1.0.0")
                                .license(new License().name("Apache 2.0")))
                        .externalDocs(new ExternalDocumentation()
                                .description("Find out more about this service")
                                .url("https://example.com/inventory-service"));
    }
}
