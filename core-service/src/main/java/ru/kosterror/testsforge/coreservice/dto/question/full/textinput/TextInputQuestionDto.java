package ru.kosterror.testsforge.coreservice.dto.question.full.textinput;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TextInputQuestionDto extends QuestionDto {

    @Schema(description = "Количество баллов за правильный ответ", requiredMode = REQUIRED)
    private Integer points;

    @Schema(description = "Является ли ответы чувствительными к регистру", requiredMode = REQUIRED)
    private boolean isCaseSensitive;

    @Schema(description = "Ответы на вопрос", requiredMode = REQUIRED)
    private List<String> answers;

    public TextInputQuestionDto() {
        super(QuestionType.TEXT_INPUT);
    }
}
