package ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewStaticBlockDto.class, name = "STATIC"),
        @JsonSubTypes.Type(value = NewDynamicBlockDto.class, name = "DYNAMIC"),
        @JsonSubTypes.Type(value = CreateBlockBasedOnExistingDto.class, name = "BASED_ON_EXISTING")
})
@Data
public abstract class CreateBlockDto {

    @NotNull(message = "Тип блока не может быть null")
    @Schema(description = "Тип блока", requiredMode = Schema.RequiredMode.REQUIRED)
    private NewBlockType type;

    protected CreateBlockDto(NewBlockType type) {
        this.type = type;
    }

}
