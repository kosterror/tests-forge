package ru.kosterror.testsforge.coreservice.service.processor.test;

import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.CheckTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.pattern.TestPatternEntity;

import java.util.Map;

public interface GeneratedTestProcessor {

    void markAnswers(GeneratedTestEntity generatedTest, AnswersDto answers);

    void markAnswersAndCalculatePoints(GeneratedTestEntity generatedTest,
                                       TestPatternEntity testPattern,
                                       AnswersDto answers
    );

    void verifyTest(GeneratedTestEntity generatedTest, CheckTestDto checkTestDto);

    void calculateMark(GeneratedTestEntity generatedTest, Map<Integer, String> marks);
}
