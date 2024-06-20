package ru.kosterror.testsforge.coreservice.client;

import ru.kosterror.testsforge.commonmodel.user.FoundGroupsDto;
import ru.kosterror.testsforge.commonmodel.user.FoundUsersDto;

import java.util.Set;
import java.util.UUID;

public interface UserClient {

    FoundGroupsDto getGroupsByIds(Set<UUID> groupIds);

    FoundUsersDto getUsersByIds(Set<UUID> userIds);
}
