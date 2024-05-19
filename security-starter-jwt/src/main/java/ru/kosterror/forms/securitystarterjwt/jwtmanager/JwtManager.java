package ru.kosterror.forms.securitystarterjwt.jwtmanager;

import ru.kosterror.forms.securitystarterjwt.model.JwtPrincipal;

public interface JwtManager {

    boolean isValid(String token);

    JwtPrincipal parse(String token);

}
