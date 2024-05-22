package ru.kosterror.forms.securitystarter.jwtmanager;

import ru.kosterror.forms.securitystarter.model.JwtUser;

public interface JwtManager {

    JwtUser parse(String token);

}
