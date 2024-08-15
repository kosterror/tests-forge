package ru.kosterror.testsforge.coreservice.service.util;

import ru.kosterror.testsforge.coreservice.dto.question.full.QuestionDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.question.QuestionEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface TestUtilService {

    List<QuestionEntity> extractQuestionEntities(TestPatternEntity testPattern);

    List<Question> extractQuestions(GeneratedTestEntity generatedTest);

    Map<UUID, QuestionDto> extractQuestionDtoMap(TestPatternEntity generatedTest);

    Question findQuestionById(List<Question> questions, UUID questionId);

}
