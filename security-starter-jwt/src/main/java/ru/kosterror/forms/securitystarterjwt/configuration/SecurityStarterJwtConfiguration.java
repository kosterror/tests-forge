package ru.kosterror.forms.securitystarterjwt.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import ru.kosterror.forms.securitystarterjwt.jwtmanager.JwtManager;
import ru.kosterror.forms.securitystarterjwt.jwtmanager.impl.JwtMangerImpl;
import ru.kosterror.forms.securitystarterjwt.keyprovider.PublicKeyProvider;
import ru.kosterror.forms.securitystarterjwt.keyprovider.impl.RSAPublicKeyProviderImpl;
import ru.kosterror.forms.securitystarterjwt.security.JwtAuthenticationConverter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@AutoConfiguration
public class SecurityStarterJwtConfiguration {

    @Value("${jwt.public-key-name}")
    private String keyName;

    @Bean
    @ConditionalOnMissingBean
    public PublicKeyProvider publicKeyProvider()
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return new RSAPublicKeyProviderImpl(keyName);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtManager jwtManager(PublicKeyProvider publicKeyProvider) {
        return new JwtMangerImpl(publicKeyProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationConverter jwtAuthenticationConverter(JwtManager manager) {
        return new JwtAuthenticationConverter(manager);
    }

}
