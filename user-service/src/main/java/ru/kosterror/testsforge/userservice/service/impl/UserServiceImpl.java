package ru.kosterror.testsforge.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.commonmodel.user.FoundUsersDto;
import ru.kosterror.testsforge.commonmodel.user.UserDto;
import ru.kosterror.testsforge.commonmodel.user.UserRole;
import ru.kosterror.testsforge.userservice.entity.UserEntity;
import ru.kosterror.testsforge.userservice.entity.UserEntity_;
import ru.kosterror.testsforge.userservice.exception.NotFoundException;
import ru.kosterror.testsforge.userservice.mapper.UserMapper;
import ru.kosterror.testsforge.userservice.repository.UserRepository;
import ru.kosterror.testsforge.userservice.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUser(UUID uuid) {
        var user = findUser(uuid);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto changeRole(UUID userId, UserRole role) {
        var user = findUser(userId);
        user.setRole(role);
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserEntity findUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with userId %s not found".formatted(userId)));
    }

    @Override
    public List<UserEntity> findAllByIds(Collection<UUID> ids) {
        return userRepository.findAllById(ids);
    }

    @Override
    public PaginationResponse<UserDto> searchUsers(String name, String surname, String patronymic, int page, int size) {
        var exampleEntity = UserEntity.builder()
                .name(name)
                .surname(surname)
                .patronymic(patronymic)
                .build();

        var exampleMatcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<UserEntity> example = Example.of(exampleEntity, exampleMatcher);

        var users = userRepository.findAll(
                example,
                PageRequest.of(
                        page,
                        size,
                        Sort.Direction.ASC,
                        UserEntity_.NAME,
                        UserEntity_.SURNAME,
                        UserEntity_.PATRONYMIC
                )
        );

        return new PaginationResponse<>(
                page,
                size,
                users.getContent()
                        .stream()
                        .map(userMapper::toDto)
                        .toList()
        );
    }

    @Override
    public FoundUsersDto getUserByIds(Set<UUID> userIds) {
        var users = userRepository.findAllById(userIds);
        var foundUserIds = users.stream().map(UserEntity::getId).collect(Collectors.toSet());
        var notFoundUserIds = new HashSet<>(userIds);
        foundUserIds.forEach(notFoundUserIds::remove);

        return new FoundUsersDto(
                users.stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toSet()),
                notFoundUserIds
        );
    }
}
