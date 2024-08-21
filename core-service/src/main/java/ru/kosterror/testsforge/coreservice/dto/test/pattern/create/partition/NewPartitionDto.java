package ru.kosterror.testsforge.coreservice.dto.test.pattern.create.partition;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block.CreateBlockDto;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewPartitionDto extends CreatePartitionDto {

    @NotNull(message = "Название раздела не может быть null")
    @Schema(description = "Название раздела", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Описание раздела", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @NotNull(message = "Список блоков не может быть null")
    @Size(min = 1, message = "Количество блоков должно содержать хотя бы одно значение")
    @Schema(description = "Список блоков", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<@Valid CreateBlockDto> blocks;

    public NewPartitionDto() {
        super(NewPartitionType.NEW);
    }

}
