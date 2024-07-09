package ru.kosterror.testsforge.coreservice.dto.test.pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdatePartitionDto(
        @NotNull(message = "Название раздела не может быть null")
        @Schema(description = "Название раздела", requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @Schema(description = "Описание раздела", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String description,

        @NotNull(message = "Список блоков не может быть null")
        @Size(min = 1, message = "Количество блоков должно содержать хотя бы одно значение")
        @Schema(description = "Список блоков", requiredMode = Schema.RequiredMode.REQUIRED)
        List<@Valid UpdateBlockDto> blocks
) {
}
