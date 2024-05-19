package ru.kosterror.forms.securitystarterjwt.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import ru.kosterror.forms.securitystarterjwt.handler.CustomAccessDeniedHandler;
import ru.kosterror.forms.securitystarterjwt.handler.CustomAuthenticationEntryPoint;
import ru.kosterror.forms.securitystarterjwt.jwtmanager.JwtManager;
import ru.kosterror.forms.securitystarterjwt.jwtmanager.impl.JwtMangerImpl;
import ru.kosterror.forms.securitystarterjwt.keyprovider.PublicKeyProvider;
import ru.kosterror.forms.securitystarterjwt.keyprovider.impl.RSAPublicKeyProviderImpl;
import ru.kosterror.forms.securitystarterjwt.security.JwtAuthenticationConverter;
import ru.kosterror.forms.securitystarterjwt.util.Beans;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@AutoConfiguration
public class SecurityStarterJwtConfiguration {

    @Value("${jwt.access-token.public-key-name}")
    private String keyName;

    @Bean(Beans.ACCESS_TOKEN_PUBLIC_KEY_PROVIDER)
    @ConditionalOnMissingBean(name = Beans.ACCESS_TOKEN_PUBLIC_KEY_PROVIDER)
    public PublicKeyProvider publicKeyProvider()
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return new RSAPublicKeyProviderImpl(keyName);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtManager jwtManager(
            @Qualifier(Beans.ACCESS_TOKEN_PUBLIC_KEY_PROVIDER) PublicKeyProvider publicKeyProvider
    ) {
        return new JwtMangerImpl(publicKeyProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationConverter jwtAuthenticationConverter(JwtManager manager) {
        return new JwtAuthenticationConverter(manager);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
        return new CustomAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return new CustomAccessDeniedHandler(objectMapper);
    }

}
