package ru.kosterror.forms.securitystarterjwt.jwtmanager;

import ru.kosterror.forms.securitystarterjwt.model.JwtUser;

public interface JwtManager {

    boolean isValid(String token);

    JwtUser parse(String token);

}
