package ru.kosterror.testsforge.coreservice.entity.test.generated.question;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MathcingQuestion extends Question {

    private List<MatchingOption> terms;

    private List<MatchingOption> definitions;

    private Map<Integer, Integer> enteredAnswersIndexes;

    public MathcingQuestion(UUID id,
                            String name,
                            List<UUID> attachments,
                            List<MatchingOption> terms,
                            List<MatchingOption> definitions) {
        super(id, name, QuestionType.MATCHING, attachments, null);
        this.terms = terms;
        this.definitions = definitions;
    }
}

