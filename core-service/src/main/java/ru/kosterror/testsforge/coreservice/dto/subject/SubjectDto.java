package ru.kosterror.testsforge.coreservice.dto.subject;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record SubjectDto(
        @Schema(description = "Идентификатор предмета", requiredMode = REQUIRED)
        UUID id,

        @Schema(description = "Название предмета", requiredMode = REQUIRED)
        String name
) {
}
