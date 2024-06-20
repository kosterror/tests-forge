package ru.kosterror.testsforge.securitystarter.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import ru.kosterror.testsforge.securitystarter.handler.CustomAccessDeniedHandler;
import ru.kosterror.testsforge.securitystarter.handler.CustomAuthenticationEntryPoint;

@AutoConfiguration
public class CommonAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "security.common.autoconfigure", havingValue = "true")
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
        return new CustomAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    @ConditionalOnProperty(name = "security.common.autoconfigure", havingValue = "true")
    public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return new CustomAccessDeniedHandler(objectMapper);
    }

}
