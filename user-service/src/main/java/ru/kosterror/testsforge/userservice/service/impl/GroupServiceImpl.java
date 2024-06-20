package ru.kosterror.testsforge.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.commonmodel.user.FoundGroupsDto;
import ru.kosterror.testsforge.commonmodel.user.GroupDto;
import ru.kosterror.testsforge.userservice.dto.UpdateGroupDto;
import ru.kosterror.testsforge.userservice.entity.GroupEntity;
import ru.kosterror.testsforge.userservice.exception.NotFoundException;
import ru.kosterror.testsforge.userservice.mapper.GroupMapper;
import ru.kosterror.testsforge.userservice.repository.GroupRepository;
import ru.kosterror.testsforge.userservice.service.GroupService;
import ru.kosterror.testsforge.userservice.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final UserService userService;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    @Transactional
    public GroupDto createGroup(UpdateGroupDto updateGroupDto) {
        var group = groupMapper.toEntity(updateGroupDto);
        var users = userService.findAllByIds(updateGroupDto.userIds());
        group.setUsers(new HashSet<>(users));

        group = groupRepository.save(group);

        return groupMapper.toDto(group);
    }

    @Override
    @Transactional
    public GroupDto getGroup(UUID groupId) {
        var groupEntity = getGroupEntity(groupId);
        return groupMapper.toDto(groupEntity);
    }

    private GroupEntity getGroupEntity(UUID groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group '%s' not found".formatted(groupId)));
    }

    @Override
    @Transactional
    public GroupDto updateGroup(UUID id, UpdateGroupDto groupDto) {
        var group = getGroupEntity(id);
        group.setName(groupDto.name());

        group.setUsers(new HashSet<>(
                        userService.findAllByIds(groupDto.userIds())
                )
        );
        group = groupRepository.save(group);

        return groupMapper.toDto(group);
    }

    @Override
    @Transactional
    public void deleteGroup(UUID groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    @Transactional
    public PaginationResponse<GroupDto> getGroups(String name, int page, int size) {
        var exampleEntity = GroupEntity.builder()
                .name(name)
                .build();

        var exampleMatcher = ExampleMatcher
                .matching()
                .withMatcher("name", match -> match.ignoreCase().contains());
        Example<GroupEntity> example = Example.of(
                exampleEntity,
                exampleMatcher
        );

        var groupPage = groupRepository.findAll(example, PageRequest.of(page, size));

        return new PaginationResponse<>(
                page,
                size,
                groupPage.getContent()
                        .stream()
                        .map(groupMapper::toDto)
                        .toList()
        );
    }

    @Override
    @Transactional
    public List<GroupDto> getUserGroups(UUID userId) {
        var user = userService.findUser(userId);
        return user.getGroups()
                .stream()
                .map(groupMapper::toDto)
                .toList();
    }

    @Override
    public FoundGroupsDto getGroupsByIds(Set<UUID> groupIds) {
        var groups = groupRepository.findAllById(groupIds);
        var foundGroupIds = groups.stream().map(GroupEntity::getId).toList();
        var notFoundGroupIds = new HashSet<>(groupIds);
        foundGroupIds.forEach(notFoundGroupIds::remove);

        return new FoundGroupsDto(
                groups.stream()
                        .map(groupMapper::toDto)
                        .collect(Collectors.toSet()),
                notFoundGroupIds
        );
    }
}
