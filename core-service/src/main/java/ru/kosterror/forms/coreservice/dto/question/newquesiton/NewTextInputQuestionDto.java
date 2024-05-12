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
public class NewTextInputQuestionDto extends NewQuestionDto {

    @Schema(description = "Является ли ответ чувствительным к регистру", requiredMode = REQUIRED)
    @NotNull(message = "Field isCaseSensitive is required")
    private boolean isCaseSensitive;

    @Schema(description = "Список правильных ответов", requiredMode = REQUIRED)
    @NotNull(message = "List of answers is required")
    @Size(min = 1, message = "List of answers must contain at least 1 element")
    private List<String> answers;

    public NewTextInputQuestionDto() {
        super(QuestionType.TEXT_INPUT);
    }
}
