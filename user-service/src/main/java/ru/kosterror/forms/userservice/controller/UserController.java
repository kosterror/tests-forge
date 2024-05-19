package ru.kosterror.forms.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.forms.securitystarterjwt.model.JwtPrincipal;
import ru.kosterror.forms.userservice.dto.UserDto;
import ru.kosterror.forms.userservice.entity.UserRole;
import ru.kosterror.forms.userservice.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить информацию о себе")
    @GetMapping
    public UserDto getUser(@AuthenticationPrincipal JwtPrincipal principal) {
        return userService.getUser(principal.userId());
    }

    @Operation(summary = "Получить информацию о пользователе")
    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @Operation(summary = "Изменить роль пользователя")
    @PutMapping("/{userId}/role")
    public UserDto changeRole(@PathVariable UUID userId, @RequestParam UserRole role) {
        return userService.changeRole(userId, role);
    }

}
