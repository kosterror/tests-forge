package ru.kosterror.forms.securitystarter.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import ru.kosterror.forms.securitystarter.model.ApiKeyAuthentication;

@Slf4j
public class ApiKeyAuthenticationConverter implements AuthenticationConverter {

    private static final String API_KEY = "Api-Key";

    private final String apiKey;

    public ApiKeyAuthenticationConverter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        String passedApiKey = request.getHeader(API_KEY);

        if (passedApiKey == null || passedApiKey.isEmpty()) {
            return null;
        }

        if (!passedApiKey.equals(apiKey)) {
            log.error("Incorrect Api-Key '{}'", passedApiKey);
            return null;
        }

        return new ApiKeyAuthentication(passedApiKey);
    }

}
