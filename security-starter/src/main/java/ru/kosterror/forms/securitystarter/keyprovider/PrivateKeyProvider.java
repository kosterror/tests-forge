package ru.kosterror.forms.securitystarter.keyprovider;

import java.security.PrivateKey;

@FunctionalInterface
public interface PrivateKeyProvider {

    PrivateKey getPrivateKey();

}
