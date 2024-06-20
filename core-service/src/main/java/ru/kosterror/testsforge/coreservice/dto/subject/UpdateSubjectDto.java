package ru.kosterror.testsforge.coreservice.dto.subject;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record UpdateSubjectDto(
        @Schema(description = "Название предмета", requiredMode = REQUIRED)
        @NotBlank(message = "Название предмета не может быть пустым")
        String name
) {
}
