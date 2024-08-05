package ru.kosterror.testsforge.coreservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.coreservice.client.UserClient;
import ru.kosterror.testsforge.coreservice.service.UserService;

import java.util.List;
import java.util.UUID;

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
}

