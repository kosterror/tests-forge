package ru.kosterror.forms.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record TokensDto(
        @Schema(description = "Access токен", requiredMode = REQUIRED)
        String accessToken,

        @Schema(description = "Refresh токен", requiredMode = REQUIRED)
        String refreshToken
) {
}
