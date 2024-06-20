package ru.kosterror.testsforge.securitystarter.keyprovider.impl;

import org.springframework.core.io.ClassPathResource;
import ru.kosterror.testsforge.securitystarter.keyprovider.PublicKeyProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAPublicKeyProviderImpl implements PublicKeyProvider {

    public static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
    public static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";
    public static final String EMPTY_STRING = "";
    public static final String SPACE_REGEX = "\\s";
    public static final String ALGORITHM = "RSA";
    private final PublicKey publicKey;

    public RSAPublicKeyProviderImpl(String keyName)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        ClassPathResource resource = new ClassPathResource(keyName);
        String key = new String(Files.readAllBytes(Paths.get(resource.getURI())));

        key = key.replace(BEGIN_PUBLIC_KEY, EMPTY_STRING)
                .replace(END_PUBLIC_KEY, EMPTY_STRING)
                .replaceAll(SPACE_REGEX, EMPTY_STRING);

        byte[] decodedKey = Base64.getDecoder().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        publicKey = keyFactory.generatePublic(keySpec);
    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }

}
