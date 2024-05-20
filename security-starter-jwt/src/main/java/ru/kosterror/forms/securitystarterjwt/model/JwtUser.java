package ru.kosterror.forms.securitystarterjwt.model;

import java.util.UUID;

public record JwtUser(UUID userId, String email, String role) {
}
