package ru.kosterror.forms.coreservice.dto.question.newquesiton;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.forms.coreservice.entity.question.QuestionType;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewSingleChoiceQuestionDto extends NewQuestionDto {

    @Schema(description = "Список вариантов ответа", requiredMode = REQUIRED)
    @NotNull(message = "Options list is required")
    @Size(min = 2, message = "Options list must contain at least 2 elements")
    private List<String> options;

    @Schema(description = "Индекс правильного варианта ответа", requiredMode = REQUIRED)
    @NotNull(message = "Correct option index is required")
    private int correctOptionIndex;

    public NewSingleChoiceQuestionDto() {
        super(QuestionType.SINGLE_CHOICE);
    }
}
