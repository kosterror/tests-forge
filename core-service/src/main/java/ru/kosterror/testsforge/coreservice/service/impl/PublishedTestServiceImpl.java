package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.commonmodel.user.UserDto;
import ru.kosterror.testsforge.coreservice.client.UserClient;
import ru.kosterror.testsforge.coreservice.dto.test.published.*;
import ru.kosterror.testsforge.coreservice.entity.test.PublishedTestEntity;
import ru.kosterror.testsforge.coreservice.exception.BadRequestException;
import ru.kosterror.testsforge.coreservice.mapper.PublishedTestMapper;
import ru.kosterror.testsforge.coreservice.repository.PublishedTestRepository;
import ru.kosterror.testsforge.coreservice.service.MailService;
import ru.kosterror.testsforge.coreservice.service.PublishedTestService;
import ru.kosterror.testsforge.coreservice.service.TestPatternService;

import java.util.*;

import static org.springframework.util.CollectionUtils.isEmpty;
import static ru.kosterror.testsforge.coreservice.specificaiton.PublishedTestSpecification.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublishedTestServiceImpl implements PublishedTestService {

    private final UserClient userClient;
    private final TestPatternService testPatternService;
    private final PublishedTestRepository publishedTestRepository;
    private final MailService mailService;
    private final PublishedTestMapper publishedTestMapper;

    @Override
    @Transactional
    public BasePublishedTestDto publishTest(PublishTestDto publishTestDto) {
        var formPattern = testPatternService.getFormPatternEntity(publishTestDto.getTestPatternId());
        var emails = getUserEmails(publishTestDto.getGroupIds(), publishTestDto.getUserIds());

        var publishedTest = PublishedTestEntity.builder()
                .deadline(publishTestDto.getDeadline())
                .timer(publishTestDto.getTimer())
                .groupIds(new ArrayList<>(publishTestDto.getGroupIds()))
                .userIds(new ArrayList<>(publishTestDto.getUserIds()))
                .testPattern(formPattern)
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

        var appliedEmails = getUserEmails(updatePublishedTestDto.groupIds(), updatePublishedTestDto.userIds());
        var newEmails = getNewEmailWithUpdatingPublishedTest(appliedEmails, publishedTest);
        var notRemovedEmails = getNotRemovedEmailsWithUpdatingPublishedTest(appliedEmails, newEmails);

        var updatedAttributes = applyChanges(publishedTest, updatePublishedTestDto);

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
        var oldEmails = getUserEmails(publishedTest.getGroupIds(), publishedTest.getUserIds());

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

        return updatedAttributes;
    }

    private List<String> getUserEmails(Collection<UUID> groupIds, Collection<UUID> userIds) {
        var users = new HashSet<>(getUsersByGroupIds(groupIds));
        users.addAll(getUsersByUserIds(userIds));

        return users.stream()
                .map(UserDto::email)
                .distinct()
                .toList();
    }

    private Set<UserDto> getUsersByUserIds(Collection<UUID> userIds) {
        if (!isEmpty(userIds)) {
            var foundUsersDto = userClient.getUsersByIds(userIds);

            if (!isEmpty(foundUsersDto.notFoundUserIds())) {
                throw new BadRequestException(
                        "Users with ids %s do not exist".formatted(foundUsersDto.notFoundUserIds())
                );
            }

            return foundUsersDto.users();
        }

        log.info("Set 'userIds' is empty, skipped getting users by ids");
        return Collections.emptySet();
    }

    private Set<UserDto> getUsersByGroupIds(Collection<UUID> groupIds) {
        if (!isEmpty(groupIds)) {
            var foundGroupsDto = userClient.getGroupsByIds(groupIds);

            if (!isEmpty(foundGroupsDto.notFoundGroupIds())) {
                throw new BadRequestException(
                        "Groups with ids %s do not exist".formatted(foundGroupsDto.notFoundGroupIds())
                );
            }

            var users = new HashSet<UserDto>();

            foundGroupsDto.groups()
                    .forEach(group -> users.addAll(group.users()));

            return users;
        }

        log.info("Set 'groupIds' is empty, skipped getting groups by ids");
        return Collections.emptySet();
    }


}
