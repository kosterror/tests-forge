package ru.kosterror.forms.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record UserDto(
        @Schema(description = "Идентификатор", requiredMode = REQUIRED)
        UUID id,

        @Schema(description = "Почта", requiredMode = REQUIRED)
        String email,

        @Schema(description = "Пароль", requiredMode = REQUIRED)
        String password,

        @Schema(description = "Имя", requiredMode = REQUIRED)
        String name,

        @Schema(description = "Фамилия", requiredMode = REQUIRED)
        String surname,

        @Schema(description = "Отчество", requiredMode = NOT_REQUIRED)
        String patronymic
) {
}
