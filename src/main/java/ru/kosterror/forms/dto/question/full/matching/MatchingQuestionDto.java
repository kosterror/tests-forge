package ru.kosterror.forms.dto.question.full.matching;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.kosterror.forms.dto.question.full.QuestionDto;
import ru.kosterror.forms.entity.question.QuestionType;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MatchingQuestionDto extends QuestionDto {

    private List<TermDefinitionDto> termAndDefinitions;

    public MatchingQuestionDto() {
        super(QuestionType.MATCHING);
    }

}
