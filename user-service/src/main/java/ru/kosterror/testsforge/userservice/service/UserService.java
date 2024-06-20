package ru.kosterror.testsforge.userservice.service;

import ru.kosterror.testsforge.commonmodel.PaginationResponse;
import ru.kosterror.testsforge.commonmodel.user.FoundUsersDto;
import ru.kosterror.testsforge.commonmodel.user.UserDto;
import ru.kosterror.testsforge.commonmodel.user.UserRole;
import ru.kosterror.testsforge.userservice.entity.UserEntity;

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
