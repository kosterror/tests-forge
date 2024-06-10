package ru.kosterror.forms.userservice.service;

import ru.kosterror.forms.userservice.dto.CredentialsDto;
import ru.kosterror.forms.userservice.dto.TokensDto;
import ru.kosterror.forms.userservice.dto.UpdateUserDto;
import ru.kosterror.forms.userservice.dto.UserDto;

import java.util.UUID;

public interface AuthService {

    TokensDto login(CredentialsDto credentialsDto);

    TokensDto refresh(String refreshToken);

    UserDto registerTeacher(UpdateUserDto updateUserDto);

    TokensDto registerStudent(UpdateUserDto updateUserDto);

    void logout(UUID userId, String refreshToken);
}
