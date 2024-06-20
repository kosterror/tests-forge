package ru.kosterror.testsforge.commonmodel.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record GroupDto(

        @Schema(description = "Идентификатор группы", requiredMode = REQUIRED)
        UUID id,

        @Schema(description = "Имя группы", requiredMode = REQUIRED)
        String name,

        @Schema(description = "Список пользователей")
        Set<UserDto> users) {
}
