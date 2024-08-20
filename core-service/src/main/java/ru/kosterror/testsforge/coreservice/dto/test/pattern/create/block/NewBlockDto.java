package ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class NewBlockDto extends CreateBlockDto {

    @NotNull(message = "Название блока не может быть null")
    @Schema(description = "Название блока", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Описание блока", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    protected NewBlockDto(NewBlockType type) {
        super(type);
    }
}
