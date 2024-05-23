package ru.kosterror.forms.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.kosterror.forms.commonmodel.PaginationResponse;
import ru.kosterror.forms.userservice.dto.UserDto;
import ru.kosterror.forms.userservice.entity.UserEntity;
import ru.kosterror.forms.userservice.entity.UserEntity_;
import ru.kosterror.forms.userservice.entity.UserRole;
import ru.kosterror.forms.userservice.exception.NotFoundException;
import ru.kosterror.forms.userservice.mapper.UserMapper;
import ru.kosterror.forms.userservice.repository.UserRepository;
import ru.kosterror.forms.userservice.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
}
