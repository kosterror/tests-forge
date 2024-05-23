package ru.kosterror.forms.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record NewGroupDto(

        @Schema(description = "Имя группы", requiredMode = REQUIRED)
        @NotBlank(message = "Имя группы не может быть пустым")
        String name,

        @Schema(description = "Множество идентификаторов пользователей", requiredMode = REQUIRED)
        @NotEmpty(message = "Список идентификаторов пользователей не может быть пустым")
        Set<UUID> userIds
) {
}
