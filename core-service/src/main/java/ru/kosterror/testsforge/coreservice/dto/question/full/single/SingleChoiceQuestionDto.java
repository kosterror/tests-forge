package ru.kosterror.testsforge.coreservice.dto.question.full.single;

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
public class SingleChoiceQuestionDto extends QuestionDto {

    @Schema(description = "Варианты ответа на вопрос", requiredMode = REQUIRED)
    private List<SingleOptionDto> options;

    @Schema(description = "Количество баллов за правильный ответ", requiredMode = REQUIRED)
    private Integer points;

    public SingleChoiceQuestionDto() {
        super(QuestionType.SINGLE_CHOICE);
    }
}
