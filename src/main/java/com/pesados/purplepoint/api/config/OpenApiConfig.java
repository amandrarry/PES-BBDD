
package com.pesados.purplepoint.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@OpenAPIDefinition(
        security = {@SecurityRequirement(name = "bearer")}
)
@SecurityScheme(name = "bearer", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
    	Server local = new Server();
    	local.setUrl("http://localhost:5001/");
    	Server prod = new Server();
        prod.setUrl("https://purplepoint.herokuapp.com/");
    	ArrayList<Server> serverList = new ArrayList<Server> ();
    	serverList.add(local);
        serverList.add(prod);

        return new OpenAPI()
                .components(new Components())
                .servers(serverList)
                .info(new Info().title("PurplePoint Application API").description(
                        "This is PurplePoint's Spring Boot API service using springdoc-openapi and OpenAPI 3.")
						.version("v1"));
    }

}