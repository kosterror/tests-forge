package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.testsforge.commonmodel.user.UserDto;
import ru.kosterror.testsforge.coreservice.client.UserClient;
import ru.kosterror.testsforge.coreservice.dto.test.published.BasePublishedTestDto;
import ru.kosterror.testsforge.coreservice.dto.test.published.PublishTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.PublishedTestEntity;
import ru.kosterror.testsforge.coreservice.exception.BadRequestException;
import ru.kosterror.testsforge.coreservice.mapper.PublishedTestMapper;
import ru.kosterror.testsforge.coreservice.repository.PublishedTestRepository;
import ru.kosterror.testsforge.coreservice.service.MailService;
import ru.kosterror.testsforge.coreservice.service.PublishedTestService;
import ru.kosterror.testsforge.coreservice.service.TestPatternService;

import java.util.*;

import static org.springframework.util.CollectionUtils.isEmpty;

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
                .formPattern(formPattern)
                .build();

        publishedTest = publishedTestRepository.save(publishedTest);
        log.info("Published test {} created", publishedTest.getId());

        mailService.sendMailsAboutPublishingTest(publishedTest.getFormPattern().getName(), emails);
        log.info("Mails about publishing test {} sent", publishedTest.getId());

        return publishedTestMapper.toBaseDto(publishedTest);
    }

    private List<String> getUserEmails(Set<UUID> groupIds, Set<UUID> userIds) {
        var users = new HashSet<>(getUsersByGroupIds(groupIds));
        users.addAll(getUsersByUserIds(userIds));

        return users.stream()
                .map(UserDto::email)
                .distinct()
                .toList();
    }

    private Set<UserDto> getUsersByUserIds(Set<UUID> userIds) {
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

    private Set<UserDto> getUsersByGroupIds(Set<UUID> groupIds) {
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
