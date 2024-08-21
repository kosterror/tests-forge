package ru.kosterror.testsforge.coreservice.dto.test.pattern.create.partition;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateBasedOnExistingPartitionDto extends CreatePartitionDto {

    @Schema(description = "Идентификатор раздела, на основе которого создается новый раздел", requiredMode = REQUIRED)
    @NotNull(message = "Идентификатор раздела не может быть null")
    private UUID partitionId;

    public CreateBasedOnExistingPartitionDto() {
        super(NewPartitionType.BASED_ON_EXISTING);
    }

}
