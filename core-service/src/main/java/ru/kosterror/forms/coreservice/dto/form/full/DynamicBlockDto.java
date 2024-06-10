package ru.kosterror.forms.coreservice.dto.form.full;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.kosterror.forms.coreservice.dto.question.full.QuestionDto;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@EqualsAndHashCode(callSuper = true)
public class DynamicBlockDto extends BlockDto {

    @Schema(description = "Название блока", requiredMode = REQUIRED)
    private List<QuestionDto> questions;

}
