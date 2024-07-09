package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.testsforge.commonmodel.user.UserDto;
import ru.kosterror.testsforge.coreservice.client.UserClient;
import ru.kosterror.testsforge.coreservice.dto.PublishTestDto;
import ru.kosterror.testsforge.coreservice.entity.test.PublishedTestEntity;
import ru.kosterror.testsforge.coreservice.exception.BadRequestException;
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

    @Override
    @Transactional
    public void publishTest(PublishTestDto publishTestDto) {
        var formPattern = testPatternService.getFormPatternEntity(publishTestDto.getFormPatternId());
        var emails = getUserEmails(publishTestDto.getGroupIds(), publishTestDto.getUserIds());

        var publishedForm = PublishedTestEntity.builder()
                .deadline(publishTestDto.getDeadline())
                .timer(publishTestDto.getTimer())
                .groupIds(new ArrayList<>(publishTestDto.getGroupIds()))
                .userIds(new ArrayList<>(publishTestDto.getUserIds()))
                .formPattern(formPattern)
                .build();

        publishedTestRepository.save(publishedForm);
        mailService.sendMailsAboutPublishingForm(publishedForm.getFormPattern().getName(), emails);
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
