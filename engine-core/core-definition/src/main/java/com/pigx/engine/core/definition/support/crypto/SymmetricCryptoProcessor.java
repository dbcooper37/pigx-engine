package com.pigx.engine.core.definition.support.crypto;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/support/crypto/SymmetricCryptoProcessor.class */
public interface SymmetricCryptoProcessor {
    String createKey();

    String decrypt(String data, String key);

    String encrypt(String data, String key);
}
