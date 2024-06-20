package ru.kosterror.testsforge.securitystarter.jwtmanager;

import ru.kosterror.testsforge.securitystarter.model.JwtUser;

public interface JwtManager {

    JwtUser parse(String token);

}
