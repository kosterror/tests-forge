package ru.kosterror.testsforge.commonmodel.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record FoundGroupsDto(
        @Schema(description = "Найденные группы", requiredMode = REQUIRED)
        Set<GroupDto> groups,

        @Schema(description = "Идентификаторы не найденных групп", requiredMode = REQUIRED)
        Set<UUID> notFoundGroupIds
) {

}
