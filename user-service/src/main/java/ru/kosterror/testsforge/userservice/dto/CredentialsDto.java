package ru.kosterror.testsforge.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record CredentialsDto(
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Некорректный формат почты")
        @NotNull(message = "Почта не может быть пустой")
        @Schema(description = "Почта", requiredMode = REQUIRED, example = "example@domain.org")
        String email,

        @NotEmpty(message = "Пароль не может быть пустым")
        @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
        @Schema(description = "Пароль", requiredMode = REQUIRED)
        String password
) {
}
