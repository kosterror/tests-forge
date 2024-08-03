package ru.kosterror.testsforge.coreservice.dto.test.pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.block.BlockType;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
public abstract class BlockDto {

    @Schema(description = "Идентификатор", requiredMode = REQUIRED)
    private UUID id;

    @Schema(description = "Название блока", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Описание блока", requiredMode = NOT_REQUIRED)
    private String description;

    @Schema(description = "Тип блока")
    private BlockType type;

}
