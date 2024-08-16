package ru.kosterror.testsforge.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record ChangePasswordDto(
        @NotEmpty(message = "Старый пароль не должен быть пустым")
        @Schema(description = "Старый пароль", requiredMode = REQUIRED)
        String oldPassword,

        @NotNull(message = "Новый пароль не должен быть null")
        @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
        @Schema(description = "Новый пароль", requiredMode = REQUIRED)
        String newPassword
) {
}
