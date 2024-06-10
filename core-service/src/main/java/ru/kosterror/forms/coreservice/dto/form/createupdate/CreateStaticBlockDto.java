package ru.kosterror.forms.coreservice.dto.form.createupdate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.forms.coreservice.entity.BlockType;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateStaticBlockDto extends CreateBlockDto {

    @NotNull(message = "Список вариантов блока не может быть null")
    @Size(min = 1, message = "Количество вариантов должно быть не меньше 1")
    @Schema(description = "Список вариантов блока", requiredMode = REQUIRED)
    private List<@Valid CreateBlockVariantDto> variants;

    protected CreateStaticBlockDto() {
        super(BlockType.STATIC);
    }

}
