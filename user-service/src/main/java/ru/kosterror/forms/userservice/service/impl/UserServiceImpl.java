package ru.kosterror.forms.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kosterror.forms.userservice.dto.UserDto;
import ru.kosterror.forms.userservice.entity.UserEntity;
import ru.kosterror.forms.userservice.entity.UserRole;
import ru.kosterror.forms.userservice.exception.NotFoundException;
import ru.kosterror.forms.userservice.mapper.UserMapper;
import ru.kosterror.forms.userservice.repository.UserRepository;
import ru.kosterror.forms.userservice.service.UserService;

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
}
