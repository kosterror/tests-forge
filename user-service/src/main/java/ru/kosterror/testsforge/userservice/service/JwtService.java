package ru.kosterror.testsforge.userservice.service;

import ru.kosterror.testsforge.userservice.dto.TokensDto;
import ru.kosterror.testsforge.userservice.entity.UserEntity;

public interface JwtService {

    TokensDto generateTokens(UserEntity user);

}
