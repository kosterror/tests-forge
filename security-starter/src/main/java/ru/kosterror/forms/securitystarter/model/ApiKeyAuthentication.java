package ru.kosterror.forms.securitystarter.model;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    private final String apiKey;

    public ApiKeyAuthentication(String apiKey) {
        super(Collections.singleton(new SimpleGrantedAuthority("ROLE_SERVICE")));
        this.apiKey = apiKey;
    }

    @Override
    public Object getCredentials() {
        return getPrincipal();
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }

}
