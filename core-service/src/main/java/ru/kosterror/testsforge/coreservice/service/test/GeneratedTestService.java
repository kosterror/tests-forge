package ru.kosterror.testsforge.coreservice.service.test;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.GeneratedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.MyGeneratedTestDto;

import java.util.UUID;

public interface GeneratedTestService {

    GeneratedTestDto getMyGeneratedTest(UUID userId, UUID publishedTestId);

    GeneratedTestDto saveAnswers(UUID userId, UUID publishedTestId, UUID generatedTestId, AnswersDto answers);

    MyGeneratedTestDto submitTest(UUID userId, UUID publishedTestId, UUID generatedTestId, AnswersDto answers);

    PaginationResponse<MyGeneratedTestDto> getMyGeneratedTests(UUID userId, UUID subjectId, int page, int size);
}
