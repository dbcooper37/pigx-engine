package com.pigx.engine.core.foundation.support.crypto;

import com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor;
import cn.hutool.v7.core.codec.binary.HexUtil;
import cn.hutool.v7.crypto.bc.SmUtil;
import cn.hutool.v7.crypto.symmetric.SM4;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/support/crypto/SM4CryptoProcessor.class */
public class SM4CryptoProcessor implements SymmetricCryptoProcessor {
    private static final Logger log = LoggerFactory.getLogger(SM4CryptoProcessor.class);

    @Override // com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor
    public String createKey() {
        SM4 sm4 = SmUtil.sm4();
        SecretKey secretKey = sm4.getSecretKey();
        byte[] encoded = secretKey.getEncoded();
        String result = HexUtil.encodeStr(encoded);
        log.trace("[Herodotus] |- SM4 crypto create hex key, value is : [{}]", result);
        return result;
    }

    @Override // com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor
    public String decrypt(String data, String key) {
        SM4 sm4 = SmUtil.sm4(HexUtil.decode(key));
        log.trace("[Herodotus] |- SM4 crypto decrypt data [{}] with key : [{}]", data, key);
        String result = sm4.decryptStr(data);
        log.trace("[Herodotus] |- SM4 crypto decrypt result is : [{}]", result);
        return result;
    }

    @Override // com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor
    public String encrypt(String data, String key) {
        SM4 sm4 = SmUtil.sm4(HexUtil.decode(key));
        log.trace("[Herodotus] |- SM4 crypto encrypt data [{}] with key : [{}]", data, key);
        String result = sm4.encryptHex(data);
        log.trace("[Herodotus] |- SM4 crypto encrypt result is : [{}]", result);
        return result;
    }
}
