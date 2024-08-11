package ru.kosterror.testsforge.coreservice.service.processor.test;

import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;

public interface GeneratedTestProcessor {

    void markAnswers(GeneratedTestEntity generatedTest, AnswersDto answers);

}
