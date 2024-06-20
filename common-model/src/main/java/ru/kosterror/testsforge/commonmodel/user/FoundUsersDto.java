package ru.kosterror.testsforge.commonmodel.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record FoundUsersDto(
        @Schema(description = "Найденные пользователи", requiredMode = REQUIRED)
        Set<UserDto> users,

        @Schema(description = "Идентификаторы не найденных пользователей", requiredMode = REQUIRED)
        Set<UUID> notFoundUserIds
) {
}
