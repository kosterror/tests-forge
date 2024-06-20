package ru.kosterror.testsforge.userservice.service;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.commonmodel.user.FoundGroupsDto;
import ru.kosterror.testsforge.commonmodel.user.GroupDto;
import ru.kosterror.testsforge.userservice.dto.UpdateGroupDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GroupService {

    GroupDto createGroup(UpdateGroupDto updateGroupDto);

    GroupDto getGroup(UUID groupId);

    GroupDto updateGroup(UUID id, UpdateGroupDto groupDto);

    void deleteGroup(UUID groupId);

    PaginationResponse<GroupDto> getGroups(String name, int page, int size);

    List<GroupDto> getUserGroups(UUID userId);

    FoundGroupsDto getGroupsByIds(Set<UUID> groupIds);
}
