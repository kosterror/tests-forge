package ru.kosterror.testsforge.coreservice.client;

import ru.kosterror.testsforge.commonmodel.user.FoundGroupsDto;
import ru.kosterror.testsforge.commonmodel.user.FoundUsersDto;

import java.util.Collection;
import java.util.UUID;

public interface UserClient {

    FoundGroupsDto getGroupsByIds(Collection<UUID> groupIds);

    FoundUsersDto getUsersByIds(Collection<UUID> userIds);
}
