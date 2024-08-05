package ru.kosterror.testsforge.coreservice.service.impl.factory;

import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.*;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.MatchingQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.textinput.TextInputQuestionEntity;

import java.util.List;

@Component
public class QuestionFactory {

    public List<Question> buildQuestions(List<QuestionEntity> questions) {
        return questions.stream()
                .map(this::buildQuestion)
                .toList();
    }

    public Question buildQuestion(QuestionEntity questionEntity) {
        return switch (questionEntity.getType()) {
            case SINGLE_CHOICE -> buildSingleChoiceQuestion((SingleChoiceQuestionEntity) questionEntity);
            case MULTIPLE_CHOICE -> buildMultipleChoiceQuestion((MultipleChoiceQuestionEntity) questionEntity);
            case TEXT_INPUT -> buildTextInputQuestion((TextInputQuestionEntity) questionEntity);
            case MATCHING -> buildMatchingQuestion((MatchingQuestionEntity) questionEntity);
        };
    }

    private SingleChoiceQuestion buildSingleChoiceQuestion(SingleChoiceQuestionEntity questionEntity) {
        List<ChoiceOption> options = questionEntity.getOptions()
                .stream()
                .map(singleOptionEntity -> new ChoiceOption(
                                singleOptionEntity.getId(),
                                singleOptionEntity.getName()
                        )
                ).toList();

        return new SingleChoiceQuestion(
                questionEntity.getId(),
                questionEntity.getName(),
                questionEntity.getAttachments(),
                options
        );
    }

    private MultipleChoiceQuestion buildMultipleChoiceQuestion(MultipleChoiceQuestionEntity questionEntity) {
        List<ChoiceOption> options = questionEntity.getOptions()
                .stream()
                .map(option -> new ChoiceOption(
                                option.getId(),
                                option.getName()
                        )
                ).toList();

        return new MultipleChoiceQuestion(
                questionEntity.getId(),
                questionEntity.getName(),
                questionEntity.getAttachments(),
                options
        );
    }

    private TextInputQuestion buildTextInputQuestion(TextInputQuestionEntity questionEntity) {
        return new TextInputQuestion(
                questionEntity.getId(),
                questionEntity.getName(),
                questionEntity.getAttachments()
        );
    }

    private MathcingQuestion buildMatchingQuestion(MatchingQuestionEntity questionEntity) {
        var termOptions = questionEntity.getTerms()
                .stream()
                .map(term -> new MatchingOption(term.getId(), term.getText()))
                .toList();

        var definitionOptions = questionEntity.getDefinitions()
                .stream()
                .map(definition -> new MatchingOption(definition.getId(), definition.getText()))
                .toList();

        return new MathcingQuestion(questionEntity.getId(),
                questionEntity.getName(),
                questionEntity.getAttachments(),
                termOptions,
                definitionOptions
        );
    }

}
