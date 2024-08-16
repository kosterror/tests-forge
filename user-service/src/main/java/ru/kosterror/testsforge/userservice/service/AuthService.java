package ru.kosterror.testsforge.userservice.service;

import ru.kosterror.testsforge.commonmodel.user.UserDto;
import ru.kosterror.testsforge.userservice.dto.ChangePasswordDto;
import ru.kosterror.testsforge.userservice.dto.CredentialsDto;
import ru.kosterror.testsforge.userservice.dto.TokensDto;
import ru.kosterror.testsforge.userservice.dto.UpdateUserDto;

import java.util.UUID;

public interface AuthService {

    TokensDto login(CredentialsDto credentialsDto);

    TokensDto refresh(String refreshToken);

    UserDto registerTeacher(UpdateUserDto updateUserDto);

    TokensDto registerStudent(UpdateUserDto updateUserDto);

    void logout(UUID userId, String refreshToken);

    void resetPassword(String email);

    void changePassword(UUID userId, ChangePasswordDto changePasswordDto);
}
