package ru.kosterror.testsforge.coreservice.dto.question.full.multiple;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;

import java.util.List;
import java.util.Map;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MultipleChoiceQuestionDto extends QuestionDto {

    @Schema(description = "Баллы", requiredMode = REQUIRED)
    private Map<Integer, Integer> points;

    @Schema(description = "Варианты ответа для вопроса", requiredMode = REQUIRED)
    private List<MultipleOptionDto> options;

    public MultipleChoiceQuestionDto() {
        super(QuestionType.MULTIPLE_CHOICE);
    }

}
