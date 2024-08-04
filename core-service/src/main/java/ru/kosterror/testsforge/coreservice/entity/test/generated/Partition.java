package ru.kosterror.testsforge.coreservice.entity.test.generated;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class Partition {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Название", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Описание", requiredMode = NOT_REQUIRED)
    private String description;

    @Schema(description = "Блоки", requiredMode = REQUIRED)
    private List<Block> blocks;

}
