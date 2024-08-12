package ru.kosterror.testsforge.coreservice.service.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.commonmodel.user.UserDto;
import ru.kosterror.testsforge.coreservice.client.UserClient;
import ru.kosterror.testsforge.coreservice.exception.BadRequestException;
import ru.kosterror.testsforge.coreservice.service.user.UserService;

import java.util.*;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    @Override
    public boolean isAnyGroupContainsUser(List<UUID> groupIds, UUID userId) {
        var groups = userClient.getGroupsByIds(groupIds).groups();

        return groups.stream()
                .anyMatch(group -> group
                        .users()
                        .stream()
                        .anyMatch(user -> user
                                .id()
                                .equals(userId)
                        )
                );
    }

    @Override
    public List<String> getUserEmails(Collection<UUID> groupIds, Collection<UUID> userIds) {
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

