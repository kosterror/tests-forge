package ru.kosterror.testsforge.coreservice.dto.test.pattern.create.block;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.dto.test.pattern.create.variant.UpdateVariantDto;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewStaticBlockDto extends NewBlockDto {

    @NotNull(message = "Список вариантов блока не может быть null")
    @Size(min = 1, message = "Количество вариантов должно быть не меньше 1")
    @Schema(description = "Список вариантов блока", requiredMode = REQUIRED)
    private List<@Valid UpdateVariantDto> variants;

    protected NewStaticBlockDto() {
        super(NewBlockType.STATIC);
    }

}
