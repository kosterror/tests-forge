package ru.kosterror.testsforge.coreservice.factory.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.*;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.factory.question.impl.*;

@Component
@RequiredArgsConstructor
public class QuestionFactory {

    private final MatchingQuestionFactory matchingQuestionFactory;
    private final MultipleChoiceQuestionFactory multipleChoiceQuestionFactory;
    private final SingleChoiceQuestionFactory singleChoiceQuestionFactory;
    private final TextInputQuestionFactory textInputQuestionFactory;
    private final BasedOnExistingQuestionFactory basedOnExistingQuestionFactory;

    public QuestionEntity buildQuestion(CreateQuestionDto questionDto) {
        return switch (questionDto.getType()) {
            case MATCHING -> matchingQuestionFactory.buildFromDto((NewMatchingQuestionDto) questionDto);
            case MULTIPLE_CHOICE ->
                    multipleChoiceQuestionFactory.buildFromDto((NewMultipleChoiceQuestionDto) questionDto);
            case SINGLE_CHOICE -> singleChoiceQuestionFactory.buildFromDto((NewSingleChoiceQuestionDto) questionDto);
            case TEXT_INPUT -> textInputQuestionFactory.buildFromDto((NewTextInputQuestionDto) questionDto);
            case BASED_ON_EXISTING ->
                    basedOnExistingQuestionFactory.buildFromDto((CreateQuestionBasedOnExistingDto) questionDto);
        };
    }

}
