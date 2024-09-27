package com.spring.onlinebookstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI defineOpenApiInfo() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Mykhailo Kornukh");
        myContact.setEmail("mykhailo.kornukh@gmail.com");

        Info information = new Info()
                .title("Online Book Store API")
                .version("1.0")
                .description("This API exposes endpoints to manage books.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
