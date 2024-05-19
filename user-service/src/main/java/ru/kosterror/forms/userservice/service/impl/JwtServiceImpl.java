package ru.kosterror.forms.userservice.service.impl;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kosterror.forms.securitystarterjwt.keyprovider.PublicKeyProvider;
import ru.kosterror.forms.securitystarterjwt.util.CustomClaims;
import ru.kosterror.forms.userservice.configuration.PrivateKeyProvider;
import ru.kosterror.forms.userservice.dto.TokensDto;
import ru.kosterror.forms.userservice.entity.RefreshTokenEntity;
import ru.kosterror.forms.userservice.entity.UserEntity;
import ru.kosterror.forms.userservice.repository.RefreshTokenRepository;
import ru.kosterror.forms.userservice.service.JwtService;

import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PrivateKeyProvider privateKeyProvider;
    private final PublicKeyProvider publicKeyProvider;

    @Value("${jwt.access-token-expiration-time-minutes}")
    private int accessTokenExpirationTimeMin;

    @Value("${jwt.refresh-token-expiration-time-days}")
    private int refreshTokenExpirationTimeDays;

    @Override
    public TokensDto generateTokens(UserEntity user) {
        String accessToken = generateAccessToken(user);

        return new TokensDto(accessToken, generateAndSaveRefreshToken(user));
    }

    private String generateAccessToken(UserEntity user) {
        var issuedAt = new Date();
        var expiration = new Date(issuedAt.getTime() + (long) accessTokenExpirationTimeMin * 60 * 1000);

        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claim(CustomClaims.ROLE, user.getRole().name())
                .claim(CustomClaims.EMAIL, user.getEmail())
                .signWith(publicKeyProvider.getPublicKey())
                .compact();
    }

    private String generateAndSaveRefreshToken(UserEntity user) {
        var issuedAt = new Date();
        var expiration = new Date(issuedAt.getTime() + (long) refreshTokenExpirationTimeDays * 24 * 60 * 60 * 1000);

        var token = Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claim(CustomClaims.ROLE, user.getRole().name())
                .claim(CustomClaims.EMAIL, user.getEmail())
                .signWith(privateKeyProvider.getPrivateKey())
                .compact();

        var tokenEntity = RefreshTokenEntity.builder()
                .token(token)
                .owner(user)
                .issuedDate(issuedAt.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .expiredDate(expiration.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();

        refreshTokenRepository.save(tokenEntity);

        return token;
    }

}
