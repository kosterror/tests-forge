package ru.kosterror.testsforge.securitystarter.model;

import java.util.UUID;

public record JwtUser(UUID userId, String email, String role) {
}
