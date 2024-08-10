package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.dto.test.generated.AnswersDto;
import ru.kosterror.testsforge.coreservice.dto.test.generated.GeneratedTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.generated.Variant;
import ru.kosterror.testsforge.coreservice.entity.test.generated.question.Question;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;
import ru.kosterror.testsforge.coreservice.exception.ConflictException;
import ru.kosterror.testsforge.coreservice.exception.ForbiddenException;
import ru.kosterror.testsforge.coreservice.exception.NotFoundException;
import ru.kosterror.testsforge.coreservice.mapper.GeneratedTestMapper;
import ru.kosterror.testsforge.coreservice.repository.GeneratedTestRepository;
import ru.kosterror.testsforge.coreservice.service.GeneratedTestService;
import ru.kosterror.testsforge.coreservice.service.PublishedTestService;
import ru.kosterror.testsforge.coreservice.service.UserService;
import ru.kosterror.testsforge.coreservice.service.impl.factory.GeneratedTestFactory;
import ru.kosterror.testsforge.coreservice.service.processor.QuestionProcessor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratedTestServiceImpl implements GeneratedTestService {

    private final GeneratedTestRepository generatedTestRepository;
    private final PublishedTestService publishedTestService;
    private final GeneratedTestMapper generatedTestMapper;
    private final GeneratedTestFactory generatedTestFactory;
    private final UserService userService;
    private final List<QuestionProcessor> questionProcessors;

    @Override
    public GeneratedTestDto getMyGeneratedTest(UUID userId, UUID publishedTestId) {
        var publishedTest = publishedTestService.getPublishedTestEntity(publishedTestId);
        checkUserAccessForPublishedTest(publishedTest, userId);

        var generatedTest = generatedTestRepository
                .findByPublishedTestIdAndUserId(publishedTestId, userId)
                .orElse(buildAndSaveGeneratedTest(publishedTest, userId));

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

        var questions = extractQuestions(generatedTest);
        questionProcessors.forEach(processor -> processor.process(questions, answers));

        generatedTest = generatedTestRepository.save(generatedTest);

        return generatedTestMapper.toDto(generatedTest);
    }


    private List<Question> extractQuestions(GeneratedTestEntity generatedTest) {
        return generatedTest.getPartitions()
                .stream()
                .flatMap(partition -> partition.getBlocks().stream())
                .flatMap(block -> {
                            var blockQuestions = Optional.ofNullable(block.getQuestions())
                                    .stream()
                                    .flatMap(Collection::stream);

                            var variantQuestions = Optional.ofNullable(block.getVariant())
                                    .map(Variant::getQuestions)
                                    .stream()
                                    .flatMap(Collection::stream);

                            return Stream.concat(blockQuestions, variantQuestions);
                        }

                ).toList();
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
            throw new ForbiddenException("You don't have access to this published test");
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
            throw new ConflictException("Timer for this test is expired");
        }

        log.info("Timer for generated test {} is not expired", generatedTest.getId());
    }

    private void checkDeadline(PublishedTestEntity publishedTest) {
        if (publishedTest.getDeadline() != null && publishedTest.getDeadline().isBefore(LocalDateTime.now())) {
            throw new ConflictException("Deadline for this test is expired");
        }

        log.info("Deadline for published test {} is not expired", publishedTest.getId());
    }

}
