package ru.kosterror.forms.userservice.service;

import ru.kosterror.forms.userservice.dto.CredentialsDto;
import ru.kosterror.forms.userservice.dto.NewUserDto;
import ru.kosterror.forms.userservice.dto.TokensDto;
import ru.kosterror.forms.userservice.dto.UserDto;

import java.util.UUID;

public interface AuthService {

    TokensDto login(CredentialsDto credentialsDto);

    TokensDto refresh(String refreshToken);

    UserDto registerTeacher(NewUserDto newUserDto);

    TokensDto registerStudent(NewUserDto newUserDto);

    void logout(UUID userId, String refreshToken);
}
