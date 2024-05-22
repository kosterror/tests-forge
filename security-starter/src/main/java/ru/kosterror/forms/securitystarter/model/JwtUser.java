package ru.kosterror.forms.securitystarter.model;

import java.util.UUID;

public record JwtUser(UUID userId, String email, String role) {
}
