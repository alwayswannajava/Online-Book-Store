package com.spring.onlinebookstore.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:open-api.properties")
@io.swagger.v3.oas.annotations.security.SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfiguration {
    private static final String OPEN_API_TITLE = "Online Book Store API";
    private static final String OPEN_API_VERSION = "1.0";
    private static final String OPEN_API_DESCRIPTION = "This API exposes endpoints "
            + "to manage books.";

    @Value("${open.api.url}")
    private String openApiUrl;
    @Value("${open.api.description}")
    private String description;

    @Bean
    public OpenAPI defineOpenApiInfo() {
        Server server = new Server();
        server.setUrl(openApiUrl);
        server.setDescription(description);

        Info information = new Info()
                .title(OPEN_API_TITLE)
                .version(OPEN_API_VERSION)
                .description(OPEN_API_DESCRIPTION);
        return new OpenAPI()
                .info(information).servers(List.of(server));
    }
}
