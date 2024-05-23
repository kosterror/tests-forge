package ru.kosterror.forms.userservice.service;

import ru.kosterror.forms.commonmodel.PaginationResponse;
import ru.kosterror.forms.userservice.dto.GroupDto;
import ru.kosterror.forms.userservice.dto.NewGroupDto;

import java.util.List;
import java.util.UUID;

public interface GroupService {

    GroupDto createGroup(NewGroupDto newGroupDto);

    GroupDto getGroup(UUID groupId);

    GroupDto updateGroup(UUID id, NewGroupDto groupDto);

    void deleteGroup(UUID groupId);

    PaginationResponse<GroupDto> getGroups(String name, int page, int size);

    List<GroupDto> getUserGroups(UUID userId);
}
