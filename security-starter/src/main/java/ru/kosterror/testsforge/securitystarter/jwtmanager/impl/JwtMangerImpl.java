package ru.kosterror.testsforge.securitystarter.jwtmanager.impl;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import ru.kosterror.testsforge.securitystarter.jwtmanager.JwtManager;
import ru.kosterror.testsforge.securitystarter.keyprovider.PublicKeyProvider;
import ru.kosterror.testsforge.securitystarter.model.JwtUser;
import ru.kosterror.testsforge.securitystarter.util.CustomClaims;

import java.util.UUID;

@RequiredArgsConstructor
public class JwtMangerImpl implements JwtManager {

    private final PublicKeyProvider publicKeyProvider;

    @Override
    public JwtUser parse(String token) {
        var claims = Jwts.parser()
                .verifyWith(publicKeyProvider.getPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return new JwtUser(
                UUID.fromString(claims.getSubject()),
                claims.get(CustomClaims.EMAIL, String.class),
                claims.get(CustomClaims.ROLE, String.class)
        );

    }
}
