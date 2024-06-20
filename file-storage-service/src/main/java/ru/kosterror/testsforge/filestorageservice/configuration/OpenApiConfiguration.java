package ru.kosterror.testsforge.filestorageservice.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    public static final String API_KEY = "Api-Key";
    public static final String JWT = "Bearer Token";
    private static final String JWT_BEARER_FORMAT = "JWT";
    private static final String JWT_SCHEME = "bearer";
    private static final String TITLE = "file-storage-service API";
    private static final String VERSION = "1.0.0";

    @Bean
    public OpenAPI userServiceOpenAPI() {
        return new OpenAPI().info(new Info()
                .title(TITLE)
                .version(VERSION)

        ).components(new Components()
                .addSecuritySchemes(JWT,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(JWT_SCHEME)
                                .bearerFormat(JWT_BEARER_FORMAT)
                ).addSecuritySchemes(API_KEY,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(API_KEY)
                )
        );
    }


}
