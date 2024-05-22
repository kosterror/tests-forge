package ru.kosterror.forms.securitystarter.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;
import ru.kosterror.forms.securitystarter.jwtmanager.JwtManager;
import ru.kosterror.forms.securitystarter.model.JwtAuthentication;
import ru.kosterror.forms.securitystarter.model.JwtUser;

@RequiredArgsConstructor
public class JwtAuthenticationConverter implements AuthenticationConverter {

    private static final String AUTHORIZATION_SCHEME_BEARER = "Bearer";
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationConverter.class);

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
            log.warn("Not found bearer authentication token");
            return null;
        }

        String token = header.substring(AUTHORIZATION_SCHEME_BEARER.length() + 1);

        try {
            JwtUser user = jwtManager.parse(token);
            return new JwtAuthentication(user, token);
        } catch (Exception exception) {
            log.error("Error parsing token", exception);
            return null;
        }
    }

}
