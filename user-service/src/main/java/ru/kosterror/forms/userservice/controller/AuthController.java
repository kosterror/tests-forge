package ru.kosterror.forms.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.forms.commonmodel.user.UserDto;
import ru.kosterror.forms.securitystarter.model.JwtUser;
import ru.kosterror.forms.userservice.dto.CredentialsDto;
import ru.kosterror.forms.userservice.dto.TokensDto;
import ru.kosterror.forms.userservice.dto.UpdateUserDto;
import ru.kosterror.forms.userservice.service.AuthService;

import static ru.kosterror.forms.userservice.configuration.OpenApiConfiguration.JWT;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Логин")
    @PostMapping("/login")
    public TokensDto login(@RequestBody @Valid CredentialsDto credentialsDto) {
        return authService.login(credentialsDto);
    }

    @Operation(summary = "Обновление токенов")
    @PostMapping("/refresh")
    public TokensDto refresh(@RequestParam String refreshToken) {
        return authService.refresh(refreshToken);
    }

    @Operation(summary = "Выход", security = @SecurityRequirement(name = JWT))
    @PostMapping("/logout")
    public void logout(@AuthenticationPrincipal JwtUser principal,
                       @RequestParam String refreshToken) {
        authService.logout(principal.userId(), refreshToken);
    }

    @Operation(summary = "Зарегистрироваться студентом")
    @PostMapping("/students/register")
    public TokensDto registerStudent(@RequestBody @Valid UpdateUserDto updateUserDto) {
        return authService.registerStudent(updateUserDto);
    }

    @Operation(summary = "Зарегистрировать преподавателя", security = @SecurityRequirement(name = JWT))
    @PostMapping("/teachers/register")
    public UserDto registerTeacher(@RequestBody @Valid UpdateUserDto updateUserDto) {
        return authService.registerTeacher(updateUserDto);
    }

}
