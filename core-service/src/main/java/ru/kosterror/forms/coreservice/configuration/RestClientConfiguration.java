package ru.kosterror.forms.coreservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    private static final String API_KEY = "Api-Key";

    @Value("${security.api-key.key}")
    private String apiKey;

    @Bean
    public RestClient fileStorageClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8082")
                .defaultHeader(API_KEY, apiKey)
                .build();
    }

    @Bean
    public RestClient userClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader(API_KEY, apiKey)
                .build();
    }

}
