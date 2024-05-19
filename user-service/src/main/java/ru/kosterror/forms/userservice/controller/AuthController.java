package ru.kosterror.forms.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.kosterror.forms.securitystarterjwt.model.JwtPrincipal;
import ru.kosterror.forms.userservice.dto.CredentialsDto;
import ru.kosterror.forms.userservice.dto.NewUserDto;
import ru.kosterror.forms.userservice.dto.TokensDto;
import ru.kosterror.forms.userservice.dto.UserDto;
import ru.kosterror.forms.userservice.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
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

    @Operation(summary = "Выход")
    @PostMapping("/logout")
    public void logout(@AuthenticationPrincipal JwtPrincipal principal,
                       @RequestParam String refreshToken) {
        authService.logout(principal.userId(), refreshToken);
    }

    @Operation(summary = "Зарегистрироваться студентом")
    @PostMapping("/students/register")
    public TokensDto registerStudent(@RequestBody @Valid NewUserDto newUserDto) {
        return authService.registerStudent(newUserDto);
    }

    @Operation(summary = "Зарегистрировать преподавателя")
    @PostMapping("/teachers/register")
    public UserDto registerTeacher(@RequestBody @Valid NewUserDto newUserDto) {
        return authService.registerTeacher(newUserDto);
    }

}
