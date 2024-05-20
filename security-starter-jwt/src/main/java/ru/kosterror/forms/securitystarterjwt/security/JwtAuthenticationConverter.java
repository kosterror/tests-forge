package ru.kosterror.forms.securitystarterjwt.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;
import ru.kosterror.forms.securitystarterjwt.jwtmanager.JwtManager;
import ru.kosterror.forms.securitystarterjwt.model.JwtAuthentication;
import ru.kosterror.forms.securitystarterjwt.model.JwtUser;

@RequiredArgsConstructor
public class JwtAuthenticationConverter implements AuthenticationConverter {

    private static final String AUTHORIZATION_SCHEME_BEARER = "Bearer";

    private final JwtManager jwtManager;

    @Override
    public Authentication convert(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            return null;
        }

        header = header.trim();
        if (!StringUtils.startsWithIgnoreCase(header, AUTHORIZATION_SCHEME_BEARER)) {
            return null;
        }

        if (header.equalsIgnoreCase(AUTHORIZATION_SCHEME_BEARER)) {
            throw new BadCredentialsException("Not found bearer authentication token");
        }

        String token = header.substring(AUTHORIZATION_SCHEME_BEARER.length() + 1);
        if (!jwtManager.isValid(token)) {
            throw new BadCredentialsException("Token is not valid");
        }

        JwtUser user = jwtManager.parse(token);

        return new JwtAuthentication(user, token);
    }

}
