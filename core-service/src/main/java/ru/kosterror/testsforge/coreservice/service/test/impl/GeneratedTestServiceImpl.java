package ru.kosterror.testsforge.coreservice.service.test.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.generated.*;
import ru.kosterror.testsforge.coreservice.dto.test.generated.verification.VerificationGeneratedTest;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.TestStatus;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.factory.test.generated.GeneratedTestFactory;
import ru.kosterror.testsforge.coreservice.factory.test.verification.VerificationGeneratedTestFactory;
import ru.kosterror.testsforge.coreservice.mapper.GeneratedTestMapper;
import ru.kosterror.testsforge.coreservice.repository.GeneratedTestRepository;
import ru.kosterror.testsforge.coreservice.service.processor.test.GeneratedTestProcessor;
import ru.kosterror.testsforge.coreservice.service.test.GeneratedTestService;
import ru.kosterror.testsforge.coreservice.service.test.PublishedTestService;
import ru.kosterror.testsforge.coreservice.service.user.UserService;
import ru.kosterror.testsforge.coreservice.service.util.CheckerUtilService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.specificaiton.GeneratedTestSpecification.*;

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
    private final VerificationGeneratedTestFactory verificationGeneratedTestFactory;
    private final CheckerUtilService checkerUtilService;

    @Override
    public GeneratedTestDto getMyGeneratedTest(UUID userId, UUID publishedTestId) {
        var publishedTest = publishedTestService.getPublishedTestEntity(publishedTestId);
        checkerUtilService.checkUserAccessForPublishedTest(publishedTest, userId);

        var generatedTest = generatedTestRepository
                .findByPublishedTestIdAndUserId(publishedTestId, userId)
                .orElseGet(() -> buildAndSaveGeneratedTest(publishedTest, userId));

        checkerUtilService.checkGeneratedTestStatus(generatedTest);

        return generatedTestMapper.toDto(generatedTest);
    }

    @Override
    public GeneratedTestDto saveAnswers(UUID userId,
                                        UUID publishedTestId,
                                        UUID generatedTestId,
                                        AnswersDto answers) {
        var publishedTest = publishedTestService.getPublishedTestEntity(publishedTestId);
        checkerUtilService.checkUserAccessForPublishedTest(publishedTest, userId);

        var generatedTest = getGeneratedTestEntity(userId, publishedTestId, generatedTestId);

        checkerUtilService.checkDeadline(publishedTest);
        checkerUtilService.checkTimer(generatedTest);
        checkerUtilService.checkGeneratedTestStatus(generatedTest);

        generatedTestProcessor.markAnswers(generatedTest, answers);
        generatedTest.setStatus(TestStatus.IN_PROGRESS);

        generatedTest = generatedTestRepository.save(generatedTest);

        return generatedTestMapper.toDto(generatedTest);
    }

    @Override
    public MyGeneratedTestDto submitTest(UUID userId, UUID publishedTestId, UUID generatedTestId, AnswersDto answers) {
        var publishedTest = publishedTestService.getPublishedTestEntity(publishedTestId);
        checkerUtilService.checkUserAccessForPublishedTest(publishedTest, userId);
        var generatedTest = getGeneratedTestEntity(userId, publishedTestId, generatedTestId);

        checkerUtilService.checkDeadline(publishedTest);
        checkerUtilService.checkTimer(generatedTest);
        checkerUtilService.checkGeneratedTestStatus(generatedTest);

        generatedTestProcessor.markAnswersAndCalculatePoints(generatedTest,
                publishedTest.getTestPattern(),
                answers
        );
        generatedTestProcessor.calculateMark(generatedTest, publishedTest.getMarkConfiguration());

        var status = Boolean.TRUE.equals(publishedTest.getIsNeedPostModeration())
                ? TestStatus.SUBMITTED
                : TestStatus.COMPLETED;

        generatedTest.setStatus(status);
        generatedTest.setSubmitDateTime(LocalDateTime.now());

        generatedTest = generatedTestRepository.save(generatedTest);

        log.info("Generated test {} submitted successfully", generatedTestId);

        return generatedTestMapper.toMyDto(generatedTest);
    }

    @Override
    public PaginationResponse<MyGeneratedTestDto> getMyGeneratedTests(UUID userId,
                                                                      UUID subjectId,
                                                                      int page,
                                                                      int size) {
        var specification = Specification.<GeneratedTestEntity>where(null)
                .and(hasUserId(userId))
                .and(hasSubjectId(subjectId));

        var pageable = PageRequest.of(page, size);

        var generatedTests = generatedTestRepository.findAll(specification, pageable)
                .getContent()
                .stream()
                .map(generatedTestMapper::toMyDto)
                .toList();

        return new PaginationResponse<>(page, size, generatedTests);
    }

    @Override
    public PaginationResponse<SubmittedTest> getSubmittedTests(UUID userId,
                                                               UUID publishedTestId,
                                                               TestStatus status,
                                                               int page,
                                                               int size
    ) {
        var specification = Specification.<GeneratedTestEntity>where(null)
                .and(hasUserId(userId))
                .and(hasPublishedTestId(publishedTestId))
                .and(hasStatus(status));


        var pageable = PageRequest.of(page, size);

        var generatedTests = generatedTestRepository.findAll(specification, pageable)
                .getContent()
                .stream()
                .map(generatedTestMapper::toSubmittedTest)
                .toList();

        return new PaginationResponse<>(page, size, generatedTests);
    }

    @Override
    public List<UUID> getUserIdsWithUnsubmittedTests(UUID publishedTestId) {
        var publishedTest = publishedTestService.getPublishedTestEntity(publishedTestId);
        var userIdsWithAccess = userService.getUserIds(
                publishedTest.getGroupIds(),
                publishedTest.getUserIds()
        );
        var userIdsWithGeneratedTest = publishedTest
                .getGeneratedTests()
                .stream()
                .map(GeneratedTestEntity::getUserId)
                .toList();

        userIdsWithGeneratedTest.forEach(userIdsWithAccess::remove);

        return new ArrayList<>(userIdsWithAccess);
    }

    @Override
    public VerificationGeneratedTest getSubmittedTest(UUID generatedTestId) {
        var generatedTest = getGeneratedTestEntityById(generatedTestId);
        return verificationGeneratedTestFactory.build(generatedTest);
    }

    @Override
    public GeneratedTestDto verifyTest(UUID generatedTestId, CheckTestDto checkTestDto) {
        var generatedTest = getGeneratedTestEntityById(generatedTestId);
        checkerUtilService.checkTestStatusForVerification(generatedTest.getStatus(), checkTestDto.status());

        generatedTestProcessor.verifyTest(generatedTest, checkTestDto);

        generatedTest = generatedTestRepository.save(generatedTest);

        return generatedTestMapper.toDto(generatedTest);
    }

    private GeneratedTestEntity getGeneratedTestEntityById(UUID generatedTestId) {
        return generatedTestRepository
                .findById(generatedTestId)
                .orElseThrow(() -> new NotFoundException("Generated test %s not found".formatted(generatedTestId)));
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

}
