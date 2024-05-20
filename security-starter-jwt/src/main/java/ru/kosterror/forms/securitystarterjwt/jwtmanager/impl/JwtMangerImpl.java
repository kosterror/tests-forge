package ru.kosterror.forms.securitystarterjwt.jwtmanager.impl;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import ru.kosterror.forms.securitystarterjwt.jwtmanager.JwtManager;
import ru.kosterror.forms.securitystarterjwt.keyprovider.PublicKeyProvider;
import ru.kosterror.forms.securitystarterjwt.model.JwtUser;
import ru.kosterror.forms.securitystarterjwt.util.CustomClaims;

import java.util.UUID;

@RequiredArgsConstructor
public class JwtMangerImpl implements JwtManager {

    private final PublicKeyProvider publicKeyProvider;

    @Override
    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(publicKeyProvider.getPublicKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

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
