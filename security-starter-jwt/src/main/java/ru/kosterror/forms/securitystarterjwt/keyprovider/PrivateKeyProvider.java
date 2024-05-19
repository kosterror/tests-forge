package ru.kosterror.forms.securitystarterjwt.keyprovider;

import java.security.PrivateKey;

@FunctionalInterface
public interface PrivateKeyProvider {

    PrivateKey getPrivateKey();

}
