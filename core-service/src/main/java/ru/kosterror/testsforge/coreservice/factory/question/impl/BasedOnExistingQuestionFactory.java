package ru.kosterror.testsforge.coreservice.factory.question.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kosterror.testsforge.coreservice.dto.question.create.CreateQuestionBasedOnExistingDto;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.MatchingQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.textinput.TextInputQuestionEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.repository.QuestionRepository;

@Component
@RequiredArgsConstructor
public class BasedOnExistingQuestionFactory {

    private final QuestionRepository questionRepository;
    private final MatchingQuestionFactory matchingQuestionFactory;
    private final MultipleChoiceQuestionFactory multipleChoiceQuestionFactory;
    private final SingleChoiceQuestionFactory singleChoiceQuestionFactory;
    private final TextInputQuestionFactory textInputQuestionFactory;

    public QuestionEntity buildFromDto(CreateQuestionBasedOnExistingDto questionDto) {
        var questionEntity = questionRepository
                .findById(questionDto.getQuestionId())
                .orElseThrow(() -> new NotFoundException(
                                "Question %s not found".formatted(questionDto.getQuestionId())
                        )
                );

        return switch (questionEntity.getType()) {
            case MATCHING -> matchingQuestionFactory.buildFromEntity((MatchingQuestionEntity) questionEntity);
            case MULTIPLE_CHOICE ->
                    multipleChoiceQuestionFactory.buildFromEntity((MultipleChoiceQuestionEntity) questionEntity);
            case SINGLE_CHOICE ->
                    singleChoiceQuestionFactory.buildFromEntity((SingleChoiceQuestionEntity) questionEntity);
            case TEXT_INPUT -> textInputQuestionFactory.buildFromEntity((TextInputQuestionEntity) questionEntity);
        };
    }

}
