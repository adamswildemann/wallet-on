package com.walleton.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API WalletOn")
                        .version("v1")
                        .description("API para gerenciamento da WalletOn - Carteira Digital")
                        .termsOfService("https://walleton.com/termos")
                        .contact(new Contact()
                                .name("Suporte WalletOn")
                                .email("suporte@walleton.com")
                                .url("https://walleton.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

}
