package ru.kosterror.forms.userservice.service;

import ru.kosterror.forms.userservice.dto.UserDto;
import ru.kosterror.forms.userservice.entity.UserEntity;
import ru.kosterror.forms.userservice.entity.UserRole;

import java.util.UUID;

public interface UserService {

    UserDto getUser(UUID uuid);

    UserDto changeRole(UUID userId, UserRole role);

    UserEntity findUser(UUID userId);
}
