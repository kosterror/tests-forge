package ru.kosterror.forms.userservice.configuration;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Getter
@Configuration
public class PrivateKeyProvider {

    private final PrivateKey privateKey;

    public PrivateKeyProvider(@Value("${jwt.private-key-name}") String keyName)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        ClassPathResource resource = new ClassPathResource(keyName);
        String key = new String(Files.readAllBytes(Paths.get(resource.getURI())));

        key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decodedKey = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        privateKey = keyFactory.generatePrivate(keySpec);
    }

}
