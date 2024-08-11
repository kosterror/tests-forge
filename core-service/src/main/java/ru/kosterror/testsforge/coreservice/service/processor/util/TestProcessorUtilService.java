package ru.kosterror.testsforge.coreservice.service.processor.util;

import org.springframework.data.util.Pair;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionType;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.matching.MatchingQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.multiple.MultipleOptionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleChoiceQuestionEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.single.SingleOptionEntity;

import java.util.List;
import java.util.UUID;

public interface TestProcessorUtilService<Q extends Question, E extends QuestionEntity> {

    Q findQuestion(List<Q> questions, UUID questionId);

    E findQuestionEntity(List<E> questionEntities, UUID questionId);

    List<Q> filterQuestions(List<Question> questions, QuestionType questionType, Class<Q> classToCast);

    List<E> filterQuestionEntities(List<QuestionEntity> questionEntities,
                                   QuestionType questionType,
                                   Class<E> classToCast
    );

    SingleOptionEntity getSingleOptionEntity(SingleChoiceQuestionEntity questionEntity,
                                             UUID optionId);

    MultipleOptionEntity getMultipleOptionEntity(MultipleChoiceQuestionEntity questionEntity,
                                                 UUID optionId);

    List<Pair<UUID, UUID>> extractTermDefinitionPairs(MatchingQuestionEntity questionEntity);
}
