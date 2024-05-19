package ru.kosterror.forms.securitystarterjwt.model;

import java.security.Principal;
import java.util.UUID;

public record JwtPrincipal(UUID userId, String email, String role) implements Principal {

    @Override
    public String getName() {
        return email;
    }

}
