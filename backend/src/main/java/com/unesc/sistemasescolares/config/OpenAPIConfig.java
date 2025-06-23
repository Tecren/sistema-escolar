package com.unesc.sistemasescolares.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Sistema Escolar - API",
        version = "1.0.0",
        description = "Documentação da API do Sistema Escolar",
        contact = @Contact(
            name = "guilherme"
        ),
        license = @License(
            name = "Licença MIT",
            url = "https://opensource.org/licenses/MIT"
        )
    )
)
@Configuration
public class OpenAPIConfig {
}