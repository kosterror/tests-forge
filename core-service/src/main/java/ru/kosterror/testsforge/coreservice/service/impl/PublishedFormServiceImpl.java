package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.testsforge.commonmodel.user.UserDto;
import ru.kosterror.testsforge.coreservice.client.UserClient;
import ru.kosterror.testsforge.coreservice.dto.PublishFormDto;
import ru.kosterror.testsforge.coreservice.entity.form.PublishedFormEntity;
import ru.kosterror.testsforge.coreservice.exception.BadRequestException;
import ru.kosterror.testsforge.coreservice.repository.PublishedFormRepository;
import ru.kosterror.testsforge.coreservice.service.FormPatternService;
import ru.kosterror.testsforge.coreservice.service.MailService;
import ru.kosterror.testsforge.coreservice.service.PublishedFormService;

import java.util.*;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublishedFormServiceImpl implements PublishedFormService {

    private final UserClient userClient;
    private final FormPatternService formPatternService;
    private final PublishedFormRepository publishedFormRepository;
    private final MailService mailService;

    @Override
    @Transactional
    public void publishForm(PublishFormDto publishFormDto) {
        var formPattern = formPatternService.getFormPatternEntity(publishFormDto.getFormPatternId());
        var emails = getUserEmails(publishFormDto.getGroupIds(), publishFormDto.getUserIds());

        var publishedForm = PublishedFormEntity.builder()
                .deadline(publishFormDto.getDeadline())
                .timer(publishFormDto.getTimer())
                .groupIds(new ArrayList<>(publishFormDto.getGroupIds()))
                .userIds(new ArrayList<>(publishFormDto.getUserIds()))
                .formPattern(formPattern)
                .build();

        publishedFormRepository.save(publishedForm);
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
