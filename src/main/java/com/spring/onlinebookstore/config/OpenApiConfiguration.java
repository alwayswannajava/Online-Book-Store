package com.spring.onlinebookstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
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

    @Value("${open.api.url}")
    private String openApiUrl;
    @Value("${open.api.description}")
    private String description;
    @Value("${open.api.author.credentials}")
    private String credentials;
    @Value("${open.api.author.email}")
    private String email;

    @Bean
    public OpenAPI defineOpenApiInfo() {
        Server server = new Server();
        server.setUrl(openApiUrl);
        server.setDescription(description);

        Contact myContact = new Contact();
        myContact.setName(credentials);
        myContact.setEmail(email);

        Info information = new Info()
                .title(OPEN_API_TITLE)
                .version(OPEN_API_VERSION)
                .description(OPEN_API_DESCRIPTION)
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
