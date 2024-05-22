package ru.kosterror.forms.securitystarter.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Getter
@EqualsAndHashCode(callSuper = true)
public class JwtAuthentication extends AbstractAuthenticationToken {

    private final transient JwtUser user;
    private final transient String token;

    public JwtAuthentication(JwtUser user, String token) {
        super(Collections.singleton(new SimpleGrantedAuthority(user.role())));
        super.setAuthenticated(true);
        this.user = user;
        this.token = token;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public JwtUser getPrincipal() {
        return user;
    }

}
