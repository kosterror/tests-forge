package ru.kosterror.testsforge.coreservice.service.util;

import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.TestStatus;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;

import java.util.UUID;

public interface CheckerUtilService {

    void checkUserAccessForPublishedTest(PublishedTestEntity publishedTest, UUID userId);

    void checkTimer(GeneratedTestEntity generatedTest);

    void checkDeadline(PublishedTestEntity publishedTest);

    void checkGeneratedTestStatus(GeneratedTestEntity generatedTest);

    void checkTestStatusForVerification(TestStatus oldStatus, TestStatus newStatus);
}
