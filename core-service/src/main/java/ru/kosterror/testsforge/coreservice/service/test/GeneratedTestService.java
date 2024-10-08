package ru.kosterror.testsforge.coreservice.service.test;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.generated.*;
import ru.kosterror.testsforge.coreservice.dto.test.generated.verification.VerificationGeneratedTest;
import ru.kosterror.testsforge.coreservice.entity.test.generated.TestStatus;

import java.util.List;
import java.util.UUID;

public interface GeneratedTestService {

    GeneratedTestDto getMyGeneratedTest(UUID userId, UUID publishedTestId);

    GeneratedTestDto saveAnswers(UUID userId, UUID generatedTestId, AnswersDto answers);

    MyGeneratedTestDto submitTest(UUID userId, UUID generatedTestId, AnswersDto answers);

    PaginationResponse<MyGeneratedTestDto> getMyGeneratedTests(UUID userId, UUID subjectId, int page, int size);

    PaginationResponse<SubmittedTest> getSubmittedTests(UUID userId,
                                                        UUID publishedTestId,
                                                        TestStatus status,
                                                        int page,
                                                        int size
    );

    List<UUID> getUserIdsWithUnsubmittedTests(UUID publishedTestId);

    VerificationGeneratedTest getSubmittedTest(UUID generatedTestId);

    GeneratedTestDto verifyTest(UUID generatedTestId, CheckTestDto checkTestDto);
}
