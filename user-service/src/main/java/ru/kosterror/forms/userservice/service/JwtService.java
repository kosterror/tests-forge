package ru.kosterror.forms.userservice.service;

import ru.kosterror.forms.userservice.dto.TokensDto;
import ru.kosterror.forms.userservice.entity.UserEntity;

public interface JwtService {

    TokensDto generateTokens(UserEntity user);

}
