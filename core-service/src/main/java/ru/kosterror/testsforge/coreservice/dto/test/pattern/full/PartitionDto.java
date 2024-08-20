package ru.kosterror.testsforge.coreservice.dto.test.pattern.full;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class PartitionDto {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Название раздела", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Описание раздела", requiredMode = NOT_REQUIRED)
    private String description;

    @Schema(description = "Список блоков", requiredMode = REQUIRED)
    private List<BlockDto> blocks;

}
