package ru.kosterror.forms.coreservice.dto.question.full.matching;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.forms.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.forms.coreservice.entity.question.QuestionType;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MatchingQuestionDto extends QuestionDto {

    @Schema(description = "Список терминов и их определений")
    private List<TermDefinitionDto> termAndDefinitions;

    public MatchingQuestionDto() {
        super(QuestionType.MATCHING);
    }

}
