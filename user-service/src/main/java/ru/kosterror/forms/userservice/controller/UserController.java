package ru.kosterror.forms.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.forms.commonmodel.PaginationResponse;
import ru.kosterror.forms.commonmodel.user.FoundUsersDto;
import ru.kosterror.forms.commonmodel.user.UserDto;
import ru.kosterror.forms.commonmodel.user.UserRole;
import ru.kosterror.forms.securitystarter.model.JwtUser;
import ru.kosterror.forms.userservice.service.UserService;

import java.util.Set;
import java.util.UUID;

import static ru.kosterror.forms.userservice.configuration.OpenApiConfiguration.API_KEY;
import static ru.kosterror.forms.userservice.configuration.OpenApiConfiguration.JWT;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить информацию о себе", security = @SecurityRequirement(name = JWT))
    @GetMapping
    public UserDto getUser(@AuthenticationPrincipal JwtUser principal) {
        return userService.getUser(principal.userId());
    }

    @Operation(summary = "Получить информацию о пользователе")
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @Operation(summary = "Изменить роль пользователя", security = @SecurityRequirement(name = JWT))
    @PutMapping("/{userId}/role")
    public UserDto changeRole(@PathVariable UUID userId, @RequestParam UserRole role) {
        return userService.changeRole(userId, role);
    }

    @Operation(summary = "Поиск по пользователям", security = @SecurityRequirement(name = JWT))
    @GetMapping("/search")
    public PaginationResponse<UserDto> searchUsers(@RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String surname,
                                                   @RequestParam(required = false) String patronymic,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return userService.searchUsers(name, surname, patronymic, page, size);
    }

    @Operation(summary = "Найти пользователей по идентификаторам", security = @SecurityRequirement(name = API_KEY))
    @GetMapping("/search-by-ids")
    public FoundUsersDto getGroupsByIds(@RequestParam Set<UUID> userIds) {
        return userService.getUserByIds(userIds);
    }
}
