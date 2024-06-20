package ru.kosterror.testsforge.securitystarter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import ru.kosterror.testsforge.securitystarter.security.ApiKeyAuthenticationConverter;

@AutoConfiguration
public class ApiKeyAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "security.api-key.autoconfigure", havingValue = "true")
    public ApiKeyAuthenticationConverter apiKeyAuthenticationConverter(
            @Value("${security.api-key.key}") String apiKey
    ) {
        return new ApiKeyAuthenticationConverter(apiKey);
    }

}
