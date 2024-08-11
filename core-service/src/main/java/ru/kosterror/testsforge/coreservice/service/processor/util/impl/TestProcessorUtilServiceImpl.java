package ru.kosterror.testsforge.coreservice.service.processor.util.impl;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.MatchingQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleOptionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleOptionEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.service.processor.util.TestProcessorUtilService;

import java.util.List;
import java.util.UUID;

@Service
public class TestProcessorUtilServiceImpl<Q extends Question, E extends QuestionEntity>
        implements TestProcessorUtilService<Q, E> {

    @Override
    public Q findQuestion(List<Q> questions, UUID questionId) {
        return questions.stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Question with id " + questionId + " not found"));
    }

    @Override
    public E findQuestionEntity(List<E> questionEntities, UUID questionId) {
        return questionEntities.stream()
                .filter(questionEntity -> questionEntity.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Question entity with id " + questionId + " not found"));
    }

    @Override
    public List<Q> filterQuestions(List<Question> questions, QuestionType questionType, Class<Q> classToCast) {
        return questions.stream()
                .filter(question -> question.getType().equals(questionType))
                .map(classToCast::cast)
                .toList();
    }

    @Override
    public List<E> filterQuestionEntities(List<QuestionEntity> questionEntities,
                                          QuestionType questionType,
                                          Class<E> classToCast
    ) {
        return questionEntities.stream()
                .filter(questionEntity -> questionEntity.getType().equals(questionType))
                .map(classToCast::cast)
                .toList();
    }

    @Override
    public SingleOptionEntity getSingleOptionEntity(SingleChoiceQuestionEntity questionEntity,
                                                    UUID optionId) {
        return questionEntity.getOptions()
                .stream()
                .filter(option -> option.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "Option with id %s not found for question %s".formatted(optionId, questionEntity.getId())
                ));
    }

    @Override
    public MultipleOptionEntity getMultipleOptionEntity(MultipleChoiceQuestionEntity questionEntity,
                                                        UUID optionId) {
        return questionEntity.getOptions()
                .stream()
                .filter(option -> option.getId().equals(optionId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        "Option with id %s not found for question %s".formatted(optionId, questionEntity.getId())
                ));
    }

    @Override
    public List<Pair<UUID, UUID>> extractTermDefinitionPairs(MatchingQuestionEntity questionEntity) {
        return questionEntity.getTerms()
                .stream()
                .map(term -> Pair.of(term.getId(), term.getDefinition().getId()))
                .toList();
    }

}