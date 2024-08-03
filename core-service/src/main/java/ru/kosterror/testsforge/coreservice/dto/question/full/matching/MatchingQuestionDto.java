package ru.kosterror.testsforge.coreservice.dto.question.full.matching;

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
public class MatchingQuestionDto extends QuestionDto {

    @Schema(description = "Баллы", requiredMode = REQUIRED)
    private Map<Integer, Integer> points;

    @Schema(description = "Список терминов и их определений", requiredMode = REQUIRED)
    private List<TermDefinitionDto> termAndDefinitions;

    public MatchingQuestionDto() {
        super(QuestionType.MATCHING);
    }

}
