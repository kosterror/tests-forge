package ru.kosterror.forms.coreservice.dto.formpattern.createupdate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.kosterror.forms.coreservice.entity.BlockType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateStaticBlockDto.class, name = "STATIC"),
        @JsonSubTypes.Type(value = CreateDynamicBlockDto.class, name = "DYNAMIC"),
})
@Data
public abstract class CreateBlockDto {

    @NotNull(message = "Название блока не может быть null")
    @Schema(description = "Название блока", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Описание блока", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @NotNull(message = "Тип блока не может быть null")
    @Schema(description = "Тип блока", requiredMode = Schema.RequiredMode.REQUIRED)
    private BlockType type;

    protected CreateBlockDto(BlockType type) {
        this.type = type;
    }

}
