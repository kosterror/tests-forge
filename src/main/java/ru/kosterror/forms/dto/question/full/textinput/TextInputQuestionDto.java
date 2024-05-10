package ru.kosterror.forms.dto.question.full.textinput;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.forms.dto.question.full.QuestionDto;
import ru.kosterror.forms.entity.question.QuestionType;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TextInputQuestionDto extends QuestionDto {

    @Schema(description = "Is the answer case sensitive", requiredMode = REQUIRED)
    private boolean isCaseSensitive;

    @Schema(description = "Answers for the question", requiredMode = REQUIRED)
    private List<String> answers;

    public TextInputQuestionDto() {
        super(QuestionType.TEXT_INPUT);
    }
}
