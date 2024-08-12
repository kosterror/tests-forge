package ru.kosterror.testsforge.coreservice.service.test.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.GeneratedTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestStatus;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;
import ru.kosterror.testsforge.coreservice.exception.ConflictException;
import ru.kosterror.testsforge.coreservice.exception.ForbiddenException;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.mapper.GeneratedTestMapper;
import ru.kosterror.testsforge.coreservice.repository.GeneratedTestRepository;
import ru.kosterror.testsforge.coreservice.service.factory.GeneratedTestFactory;
import ru.kosterror.testsforge.coreservice.service.processor.test.GeneratedTestProcessor;
import ru.kosterror.testsforge.coreservice.service.test.GeneratedTestService;
import ru.kosterror.testsforge.coreservice.service.test.PublishedTestService;
import ru.kosterror.testsforge.coreservice.service.user.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratedTestServiceImpl implements GeneratedTestService {

    private final GeneratedTestRepository generatedTestRepository;
    private final PublishedTestService publishedTestService;
    private final GeneratedTestMapper generatedTestMapper;
    private final GeneratedTestFactory generatedTestFactory;
    private final UserService userService;
    private final GeneratedTestProcessor generatedTestProcessor;

    @Override
    public GeneratedTestDto getMyGeneratedTest(UUID userId, UUID publishedTestId) {
        var publishedTest = publishedTestService.getPublishedTestEntity(publishedTestId);
        checkUserAccessForPublishedTest(publishedTest, userId);

        var generatedTest = generatedTestRepository
                .findByPublishedTestIdAndUserId(publishedTestId, userId)
                .orElse(buildAndSaveGeneratedTest(publishedTest, userId));

        checkGeneratedTestStatus(generatedTest);

        return generatedTestMapper.toDto(generatedTest);
    }

    @Override
    public GeneratedTestDto saveAnswers(UUID userId,
                                        UUID publishedTestId,
                                        UUID generatedTestId,
                                        AnswersDto answers) {
        var publishedTest = publishedTestService.getPublishedTestEntity(publishedTestId);
        checkUserAccessForPublishedTest(publishedTest, userId);

        var generatedTest = getGeneratedTestEntity(userId, publishedTestId, generatedTestId);

        checkDeadline(publishedTest);
        checkTimer(generatedTest);
        checkGeneratedTestStatus(generatedTest);

        generatedTestProcessor.markAnswers(generatedTest, answers);
        generatedTest.setStatus(GeneratedTestStatus.SAVED);

        generatedTest = generatedTestRepository.save(generatedTest);

        return generatedTestMapper.toDto(generatedTest);
    }

    @Override
    public void submitTest(UUID userId, UUID publishedTestId, UUID generatedTestId, AnswersDto answers) {
        var publishedTest = publishedTestService.getPublishedTestEntity(publishedTestId);
        checkUserAccessForPublishedTest(publishedTest, userId);
        var generatedTest = getGeneratedTestEntity(userId, publishedTestId, generatedTestId);

        checkDeadline(publishedTest);
        checkTimer(generatedTest);
        checkGeneratedTestStatus(generatedTest);

        generatedTestProcessor.markAnswersAndCalculatePoints(generatedTest,
                publishedTest.getTestPattern(),
                answers
        );

        generatedTest.setStatus(GeneratedTestStatus.SUBMITTED);

        generatedTestRepository.save(generatedTest);

        log.info("Generated test {} submitted successfully", generatedTestId);
    }

    private void checkGeneratedTestStatus(GeneratedTestEntity generatedTest) {
        if (generatedTest.getStatus() != GeneratedTestStatus.CREATED
                && generatedTest.getStatus() != GeneratedTestStatus.SAVED
        ) {
            throw new ConflictException("Generated test %s is already submitted".formatted(generatedTest.getId()));
        }
    }

    private GeneratedTestEntity getGeneratedTestEntity(UUID userId, UUID publishedTestId, UUID generatedTestId) {
        return generatedTestRepository
                .findByPublishedTestIdAndUserId(publishedTestId, userId)
                .orElseThrow(() -> new NotFoundException(
                                "Generated test %s for published test %s not found".formatted(
                                        generatedTestId,
                                        publishedTestId
                                )
                        )
                );
    }

    private GeneratedTestEntity buildAndSaveGeneratedTest(PublishedTestEntity publishedTest, UUID userId) {
        var generatedTest = generatedTestFactory.buildGeneratedTestEntity(publishedTest, userId);

        return generatedTestRepository.save(generatedTest);
    }

    private void checkUserAccessForPublishedTest(PublishedTestEntity publishedTest, UUID userId) {
        if (!publishedTest.getUserIds().contains(userId)
                && !userService.isAnyGroupContainsUser(publishedTest.getGroupIds(), userId
        )) {
            throw new ForbiddenException(
                    "You don't have access to published test %s".formatted(publishedTest.getId())
            );
        }

        log.info("User {} has access to published test {}", userId, publishedTest.getId());
    }

    private void checkTimer(GeneratedTestEntity generatedTest) {
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

    private void checkDeadline(PublishedTestEntity publishedTest) {
        if (publishedTest.getDeadline() != null && publishedTest.getDeadline().isBefore(LocalDateTime.now())) {
            throw new ConflictException("Deadline for published test %s is expired".formatted(publishedTest.getId()));
        }

        log.info("Deadline for published test {} is not expired", publishedTest.getId());
    }

}
