package ru.kosterror.forms.userservice.service;

import ru.kosterror.forms.commonmodel.PaginationResponse;
import ru.kosterror.forms.commonmodel.user.FoundUsersDto;
import ru.kosterror.forms.commonmodel.user.UserDto;
import ru.kosterror.forms.commonmodel.user.UserRole;
import ru.kosterror.forms.userservice.entity.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService {

    UserDto getUser(UUID uuid);

    UserDto changeRole(UUID userId, UserRole role);

    UserEntity findUser(UUID userId);

    List<UserEntity> findAllByIds(Collection<UUID> ids);

    PaginationResponse<UserDto> searchUsers(String name, String surname, String patronymic, int page, int size);

    FoundUsersDto getUserByIds(Set<UUID> userIds);
}
