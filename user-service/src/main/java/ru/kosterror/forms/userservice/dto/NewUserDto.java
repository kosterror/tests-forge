package ru.kosterror.forms.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record NewUserDto(
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Некорректный формат почты")
        @NotNull(message = "Почта не может быть пустой")
        @Schema(description = "Почта", requiredMode = REQUIRED, example = "example@domain.org")
        String email,

        @NotEmpty(message = "Пароль не может быть пустым")
        @Size(min = 8, message = "Пароль должен быть не менее 8 символов")
        @Schema(description = "Пароль", requiredMode = REQUIRED)
        String password,

        @NotEmpty(message = "Имя не может быть пустым")
        @Schema(description = "Имя", requiredMode = REQUIRED)
        String name,

        @NotEmpty(message = "Фамилия не может быть пустой")
        @Schema(description = "Фамилия", requiredMode = REQUIRED)
        String surname,

        @Schema(description = "Отчество", requiredMode = NOT_REQUIRED)
        String patronymic
) {
}
