package br.com.walleton.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig{

    @Bean
    public OpenAPI walletOnOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Wallet On API")
                        .description("API REST para gerenciamento de usuários e carteiras digitais.")
                        .version("v1.0.0"));
    }

}
