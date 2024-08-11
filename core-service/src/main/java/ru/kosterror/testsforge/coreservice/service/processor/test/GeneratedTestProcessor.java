package ru.kosterror.testsforge.coreservice.service.processor.test;

import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;

public interface GeneratedTestProcessor {

    void markAnswers(GeneratedTestEntity generatedTest, AnswersDto answers);

    void markAnswersAndCalculatePoints(GeneratedTestEntity generatedTest,
                                       TestPatternEntity testPattern,
                                       AnswersDto answers
    );
}
