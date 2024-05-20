package ru.kosterror.forms.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.forms.securitystarterjwt.model.JwtUser;
import ru.kosterror.forms.userservice.dto.UserDto;
import ru.kosterror.forms.userservice.entity.UserRole;
import ru.kosterror.forms.userservice.service.UserService;

import java.util.UUID;

import static ru.kosterror.forms.userservice.configuration.SpringDocConfiguration.JWT;

@RestController
@RequestMapping("/api/v1/users")
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

}
