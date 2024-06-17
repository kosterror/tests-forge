package ru.kosterror.forms.coreservice.client;

import ru.kosterror.forms.commonmodel.user.FoundGroupsDto;
import ru.kosterror.forms.commonmodel.user.FoundUsersDto;

import java.util.Set;
import java.util.UUID;

public interface UserClient {

    FoundGroupsDto getGroupsByIds(Set<UUID> groupIds);

    FoundUsersDto getUsersByIds(Set<UUID> userIds);
}
