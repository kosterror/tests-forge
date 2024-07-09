package ru.kosterror.testsforge.coreservice.dto.test.pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record UpdateTestPatternDto(
        @NotNull(message = "Название теста не может быть null")
        @Schema(description = "Название теста", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @NotNull(message = "Описание не может быть null")
        @Schema(description = "Описание теста", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String description,

        @Schema(description = "Идентификатор предмета", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        UUID subjectId,

        @NotNull(message = "Список разделов не может быть null")
        @Size(min = 1, message = "Количество разделов должно содержать хотя бы одно значение")
        @Schema(description = "Список разделов", requiredMode = Schema.RequiredMode.REQUIRED)
        List<@Valid UpdatePartitionDto> partitions
) {
}
