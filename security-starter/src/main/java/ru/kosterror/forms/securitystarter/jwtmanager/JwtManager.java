package ru.kosterror.forms.securitystarter.jwtmanager;

import ru.kosterror.forms.securitystarter.model.JwtUser;

public interface JwtManager {

    boolean isValid(String token);

    JwtUser parse(String token);

}
