package ru.kosterror.testsforge.coreservice.service;

import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.GeneratedTestDto;

import java.util.UUID;

public interface GeneratedTestService {

    GeneratedTestDto getMyGeneratedTest(UUID userId, UUID publishedTestId);

    GeneratedTestDto saveAnswers(UUID userId, UUID publishedTestId, UUID generatedTestId, AnswersDto answers);

    void submitTest(UUID userId, UUID publishedTestId, UUID generatedTestId, AnswersDto answers);
}
