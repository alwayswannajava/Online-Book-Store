package com.spring.onlinebookstore.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:open-api.properties")
public class OpenApiConfiguration {
    private static final String OPEN_API_TITLE = "Online Book Store API";
    private static final String OPEN_API_VERSION = "1.0";
    private static final String OPEN_API_DESCRIPTION = "This API exposes endpoints "
            + "to manage books.";
    private static final String SECURITY_AUTH_SCHEME_NAME = "bearerAuth";
    private static final String SECURITY_SCHEME_NAME = "bearer";
    private static final String SECURITY_BEARER_FORMAT = "JWT";

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
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_AUTH_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_AUTH_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_AUTH_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(SECURITY_SCHEME_NAME)
                                .bearerFormat(SECURITY_BEARER_FORMAT)))
                .info(information).servers(List.of(server));
    }
}
