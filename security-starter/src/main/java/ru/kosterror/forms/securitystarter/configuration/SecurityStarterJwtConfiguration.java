package ru.kosterror.forms.securitystarter.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import ru.kosterror.forms.securitystarter.handler.CustomAccessDeniedHandler;
import ru.kosterror.forms.securitystarter.handler.CustomAuthenticationEntryPoint;
import ru.kosterror.forms.securitystarter.jwtmanager.JwtManager;
import ru.kosterror.forms.securitystarter.jwtmanager.impl.JwtMangerImpl;
import ru.kosterror.forms.securitystarter.keyprovider.PublicKeyProvider;
import ru.kosterror.forms.securitystarter.keyprovider.impl.RSAPublicKeyProviderImpl;
import ru.kosterror.forms.securitystarter.security.JwtAuthenticationConverter;
import ru.kosterror.forms.securitystarter.util.Beans;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@AutoConfiguration
public class SecurityStarterJwtConfiguration {

    @Bean(Beans.ACCESS_TOKEN_PUBLIC_KEY_PROVIDER)
    @ConditionalOnProperty(name = "jwt.autoconfigure", havingValue = "true")
    public PublicKeyProvider publicKeyProvider(@Value("${jwt.access-token.public-key-name}") String keyName)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return new RSAPublicKeyProviderImpl(keyName);
    }

    @Bean
    @ConditionalOnProperty(name = "jwt.autoconfigure", havingValue = "true")
    public JwtManager jwtManager(
            @Qualifier(Beans.ACCESS_TOKEN_PUBLIC_KEY_PROVIDER) PublicKeyProvider publicKeyProvider
    ) {
        return new JwtMangerImpl(publicKeyProvider);
    }

    @Bean
    @ConditionalOnProperty(name = "jwt.autoconfigure", havingValue = "true")
    public JwtAuthenticationConverter jwtAuthenticationConverter(JwtManager manager) {
        return new JwtAuthenticationConverter(manager);
    }

    @Bean
    @ConditionalOnProperty(name = "jwt.autoconfigure", havingValue = "true")
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
        return new CustomAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    @ConditionalOnProperty(name = "jwt.autoconfigure", havingValue = "true")
    public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return new CustomAccessDeniedHandler(objectMapper);
    }

}
