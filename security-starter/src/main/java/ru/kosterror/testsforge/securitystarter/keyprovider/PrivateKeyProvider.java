package ru.kosterror.testsforge.securitystarter.keyprovider;

import java.security.PrivateKey;

@FunctionalInterface
public interface PrivateKeyProvider {

    PrivateKey getPrivateKey();

}
