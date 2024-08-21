package ru.kosterror.testsforge.coreservice.dto.test.pattern.create.partition;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewPartitionDto.class, name = "NEW"),
        @JsonSubTypes.Type(value = CreateBasedOnExistingPartitionDto.class, name = "BASED_ON_EXISTING")
})
@Data
public abstract class CreatePartitionDto {

    @Schema(description = "Тип нового раздела", requiredMode = REQUIRED)
    private NewPartitionType type;

    protected CreatePartitionDto(NewPartitionType type) {
        this.type = type;
    }
}
