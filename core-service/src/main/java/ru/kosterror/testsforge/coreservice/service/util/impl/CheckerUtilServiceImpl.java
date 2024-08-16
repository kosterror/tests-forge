package ru.kosterror.testsforge.coreservice.service.util.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.TestStatus;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;
import ru.kosterror.testsforge.coreservice.exception.ConflictException;
import ru.kosterror.testsforge.coreservice.exception.ForbiddenException;
import ru.kosterror.testsforge.coreservice.service.user.UserService;
import ru.kosterror.testsforge.coreservice.service.util.CheckerUtilService;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckerUtilServiceImpl implements CheckerUtilService {

    private final UserService userService;

    @Override
    public void checkUserAccessForPublishedTest(PublishedTestEntity publishedTest, UUID userId) {
        if (!publishedTest.getUserIds().contains(userId)
                && !userService.isAnyGroupContainsUser(publishedTest.getGroupIds(), userId
        )) {
            throw new ForbiddenException(
                    "You don't have access to published test %s".formatted(publishedTest.getId())
            );
        }

        log.info("User {} has access to published test {}", userId, publishedTest.getId());
    }

    @Override
    public void checkTimer(GeneratedTestEntity generatedTest) {
        var timer = generatedTest.getPublishedTest().getTimer();
        if (timer == null) {
            return;
        }

        var timerFinish = generatedTest.getCreatedAt().plusMinutes(timer);
        if (timerFinish.isBefore(LocalDateTime.now())) {
            throw new ConflictException("Timer for generated test %s is expired".formatted(generatedTest.getId()));
        }

        log.info("Timer for generated test {} is not expired", generatedTest.getId());
    }

    @Override
    public void checkDeadline(PublishedTestEntity publishedTest) {
        if (publishedTest.getDeadline() != null && publishedTest.getDeadline().isBefore(LocalDateTime.now())) {
            throw new ConflictException("Deadline for published test %s is expired".formatted(publishedTest.getId()));
        }

        log.info("Deadline for published test {} is not expired", publishedTest.getId());
    }

    @Override
    public void checkGeneratedTestStatus(GeneratedTestEntity generatedTest) {
        if (generatedTest.getStatus() != TestStatus.NEW
                && generatedTest.getStatus() != TestStatus.IN_PROGRESS
        ) {
            throw new ConflictException("Generated test %s is already submitted".formatted(generatedTest.getId()));
        }
    }

    @Override
    public void checkTestStatusForVerification(TestStatus oldStatus, TestStatus newStatus) {
        if (oldStatus != TestStatus.COMPLETED && oldStatus != TestStatus.SUBMITTED) {
            throw new ConflictException(
                    "Old status %s of generated test is not suitable for verification".formatted(oldStatus)
            );
        }

        if (newStatus != TestStatus.COMPLETED && newStatus != TestStatus.SUBMITTED) {
            throw new ConflictException(
                    "New status %s of generated test is not suitable for verification".formatted(newStatus)
            );
        }
    }

}
