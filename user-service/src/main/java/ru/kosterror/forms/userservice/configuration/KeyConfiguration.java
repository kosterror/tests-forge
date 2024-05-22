package ru.kosterror.forms.userservice.configuration;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.kosterror.forms.securitystarter.keyprovider.PrivateKeyProvider;
import ru.kosterror.forms.userservice.util.Beans;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Configuration
public class KeyConfiguration {

    private static final String ALGORITHM = "RSA";
    private static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    private static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
    private static final String REGEX = "\\s";
    private static final String EMPTY_STRING = "";

    @Value("${jwt.access-token.private-key-name}")
    private String accessTokenPrivateKeyName;

    @Value("${jwt.refresh-token.private-key-name}")
    private String refreshTokenPrivateKeyName;

    @SneakyThrows
    private static PrivateKey loadPrivateKey(String keyName) {
        ClassPathResource resource = new ClassPathResource(keyName);
        String key = new String(Files.readAllBytes(Paths.get(resource.getURI())));

        key = key.replace(BEGIN_PRIVATE_KEY, EMPTY_STRING)
                .replace(END_PRIVATE_KEY, EMPTY_STRING)
                .replaceAll(REGEX, EMPTY_STRING);

        byte[] decodedKey = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        return keyFactory.generatePrivate(keySpec);
    }

    @Bean(Beans.ACCESS_TOKEN_PRIVATE_KEY)
    public PrivateKeyProvider accessTokenPrivateKey() {
        PrivateKey privateKey = loadPrivateKey(accessTokenPrivateKeyName);
        return () -> privateKey;
    }

    @Bean(Beans.REFRESH_TOKEN_PRIVATE_KEY)
    public PrivateKeyProvider refreshTokenPrivateKey() {
        PrivateKey privateKey = loadPrivateKey(refreshTokenPrivateKeyName);
        return () -> privateKey;
    }

}
