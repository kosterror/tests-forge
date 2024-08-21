package ru.kosterror.testsforge.coreservice.service.test.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.coreservice.dto.test.published.*;
import ru.kosterror.testsforge.coreservice.entity.test.generated.GeneratedTestEntity;
import ru.kosterror.testsforge.coreservice.entity.test.published.PublishedTestEntity;
import ru.kosterror.testsforge.coreservice.exception.BadRequestException;
import ru.kosterror.testsforge.coreservice.mapper.PublishedTestMapper;
import ru.kosterror.testsforge.coreservice.repository.GeneratedTestRepository;
import ru.kosterror.testsforge.coreservice.repository.PublishedTestRepository;
import ru.kosterror.testsforge.coreservice.service.mail.MailService;
import ru.kosterror.testsforge.coreservice.service.processor.test.GeneratedTestProcessor;
import ru.kosterror.testsforge.coreservice.service.test.PublishedTestService;
import ru.kosterror.testsforge.coreservice.service.test.TestPatternService;
import ru.kosterror.testsforge.coreservice.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.kosterror.testsforge.coreservice.specificaiton.PublishedTestSpecification.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublishedTestServiceImpl implements PublishedTestService {

    private final TestPatternService testPatternService;
    private final PublishedTestRepository publishedTestRepository;
    private final MailService mailService;
    private final PublishedTestMapper publishedTestMapper;
    private final UserService userService;
    private final GeneratedTestProcessor generatedTestProcessor;
    private final GeneratedTestRepository generatedTestRepository;

    @Override
    @Transactional
    public BasePublishedTestDto publishTest(PublishTestDto publishTestDto) {
        var formPattern = testPatternService.getTestPatternEntity(publishTestDto.getTestPatternId());
        var emails = userService.getUserEmails(publishTestDto.getGroupIds(), publishTestDto.getUserIds());

        var publishedTest = PublishedTestEntity.builder()
                .deadline(publishTestDto.getDeadline())
                .timer(publishTestDto.getTimer())
                .groupIds(new ArrayList<>(publishTestDto.getGroupIds()))
                .userIds(new ArrayList<>(publishTestDto.getUserIds()))
                .showPointsToStudents(publishTestDto.getShowPointsToStudents())
                .isNeedPostModeration(publishTestDto.getIsNeedPostModeration())
                .testPattern(formPattern)
                .markConfiguration(publishTestDto.getMarkConfiguration())
                .build();

        publishedTest = publishedTestRepository.save(publishedTest);
        log.info("Published test {} created", publishedTest.getId());

        mailService.sendMailsAboutPublishingTest(publishedTest.getTestPattern().getName(), emails);
        log.info("Mails about publishing test {} sent", publishedTest.getId());

        return publishedTestMapper.toBaseDto(publishedTest);
    }

    @Override
    public PaginationResponse<BasePublishedTestDto> getPublishedTests(String name,
                                                                      UUID subjectId,
                                                                      UUID groupId,
                                                                      int page,
                                                                      int size
    ) {
        var specification = Specification.<PublishedTestEntity>where(null)
                .and(hasNameLike(name))
                .and(hasSubject(subjectId))
                .and(hasGroupId(groupId));

        var pageable = PageRequest.of(page, size);

        var publishedTests = publishedTestRepository.findAll(specification, pageable)
                .getContent()
                .stream()
                .map(publishedTestMapper::toBaseDto)
                .toList();

        return new PaginationResponse<>(page, size, publishedTests);
    }

    @Override
    public PublishedTestDto getPublishedTest(UUID id) {
        var publishedTest = getPublishedTestEntity(id);

        return publishedTestMapper.toDto(publishedTest);
    }

    @Override
    public PublishedTestEntity getPublishedTestEntity(UUID id) {
        return publishedTestRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Published test with id %s does not exist".formatted(id)));
    }

    @Override
    @Transactional
    public BasePublishedTestDto updatePublishedTest(UUID publishedTestId,
                                                    UpdatePublishedTestDto updatePublishedTestDto
    ) {
        var publishedTest = getPublishedTestEntity(publishedTestId);

        var appliedEmails = userService.getUserEmails(updatePublishedTestDto.groupIds(), updatePublishedTestDto.userIds());
        var newEmails = getNewEmailWithUpdatingPublishedTest(appliedEmails, publishedTest);
        var notRemovedEmails = getNotRemovedEmailsWithUpdatingPublishedTest(appliedEmails, newEmails);

        var updatedAttributes = applyChanges(publishedTest, updatePublishedTestDto);

        if (!publishedTest.getMarkConfiguration().equals(updatePublishedTestDto.markConfiguration())) {
            recalculateMarks(
                    publishedTest.getGeneratedTests(),
                    updatePublishedTestDto.markConfiguration()
            );
        }

        publishedTest = publishedTestRepository.save(publishedTest);
        log.info("Published test {} updated", publishedTest.getId());

        mailService.sendMailsAboutPublishingTest(publishedTest.getTestPattern().getName(), newEmails);
        mailService.sendMailsAboutUpdatingPublishedTest(publishedTest.getTestPattern().getName(),
                notRemovedEmails,
                updatedAttributes
        );
        log.info("Notification mails about updating test {} sent", publishedTest.getId());

        return publishedTestMapper.toBaseDto(publishedTest);
    }

    @Override
    @Transactional
    public void deletePublishedTest(UUID id) {
        var publishedTest = getPublishedTestEntity(id);

        publishedTestRepository.delete(publishedTest);
    }

    private ArrayList<String> getNotRemovedEmailsWithUpdatingPublishedTest(List<String> appliedEmails,
                                                                           ArrayList<String> newEmails) {
        var notRemovedEmails = new ArrayList<>(appliedEmails);
        appliedEmails.removeAll(newEmails);
        return notRemovedEmails;
    }

    private ArrayList<String> getNewEmailWithUpdatingPublishedTest(List<String> appliedEmails,
                                                                   PublishedTestEntity publishedTest
    ) {
        var oldEmails = userService.getUserEmails(publishedTest.getGroupIds(), publishedTest.getUserIds());

        var newEmails = new ArrayList<>(appliedEmails);
        newEmails.removeAll(oldEmails);

        return newEmails;
    }

    private List<PublishedTestAttribute> applyChanges(PublishedTestEntity publishedTest,
                                                      UpdatePublishedTestDto updatePublishedTestDto
    ) {
        var updatedAttributes = new ArrayList<PublishedTestAttribute>();

        if (!publishedTest.getDeadline().isEqual(updatePublishedTestDto.deadline())) {
            updatedAttributes.add(PublishedTestAttribute.DEADLINE);
        }

        if (!publishedTest.getTimer().equals(updatePublishedTestDto.timer())) {
            updatedAttributes.add(PublishedTestAttribute.TIMER);
        }

        publishedTest.setDeadline(updatePublishedTestDto.deadline());
        publishedTest.setTimer(updatePublishedTestDto.timer());
        publishedTest.setGroupIds(new ArrayList<>(updatePublishedTestDto.groupIds()));
        publishedTest.setUserIds(new ArrayList<>(updatePublishedTestDto.userIds()));
        publishedTest.setShowPointsToStudents(updatePublishedTestDto.showPointsToStudents());
        publishedTest.setMarkConfiguration(updatePublishedTestDto.markConfiguration());

        return updatedAttributes;
    }

    public void recalculateMarks(List<GeneratedTestEntity> generatedTests,
                                 Map<Integer, String> marks
    ) {
        generatedTests.forEach(generatedTest -> {
            generatedTestProcessor.calculateMark(generatedTest, marks);
            generatedTestRepository.save(generatedTest);
        });
    }

}
