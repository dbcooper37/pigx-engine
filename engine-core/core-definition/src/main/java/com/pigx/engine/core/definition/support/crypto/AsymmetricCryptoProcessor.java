package com.pigx.engine.core.definition.support.crypto;

import com.pigx.engine.core.definition.domain.SecretKey;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/support/crypto/AsymmetricCryptoProcessor.class */
public interface AsymmetricCryptoProcessor {
    SecretKey createSecretKey();

    String decrypt(String content, String privateKey);

    String encrypt(String content, String publicKey);
}
