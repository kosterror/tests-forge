package ru.kosterror.forms.userservice.service.impl;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.kosterror.forms.securitystarter.keyprovider.PrivateKeyProvider;
import ru.kosterror.forms.securitystarter.util.CustomClaims;
import ru.kosterror.forms.userservice.dto.TokensDto;
import ru.kosterror.forms.userservice.entity.RefreshTokenEntity;
import ru.kosterror.forms.userservice.entity.UserEntity;
import ru.kosterror.forms.userservice.repository.RefreshTokenRepository;
import ru.kosterror.forms.userservice.service.JwtService;
import ru.kosterror.forms.userservice.util.Beans;

import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PrivateKeyProvider accessTokenPrivateKeyProvider;
    private final PrivateKeyProvider refreshTokenPrivateKeyProvider;

    @Value("${security.jwt.access-token.expiration-time-minutes}")
    private int accessTokenExpirationTimeMin;

    @Value("${security.jwt.refresh-token.expiration-time-days}")
    private int refreshTokenExpirationTimeDays;

    public JwtServiceImpl(RefreshTokenRepository refreshTokenRepository,
                          @Qualifier(Beans.ACCESS_TOKEN_PRIVATE_KEY) PrivateKeyProvider accessTokenPrivateKeyProvider,
                          @Qualifier(Beans.REFRESH_TOKEN_PRIVATE_KEY) PrivateKeyProvider refreshTokenPrivateKeyProvider
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenPrivateKeyProvider = accessTokenPrivateKeyProvider;
        this.refreshTokenPrivateKeyProvider = refreshTokenPrivateKeyProvider;
    }

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
                .signWith(accessTokenPrivateKeyProvider.getPrivateKey())
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
                .signWith(refreshTokenPrivateKeyProvider.getPrivateKey())
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
