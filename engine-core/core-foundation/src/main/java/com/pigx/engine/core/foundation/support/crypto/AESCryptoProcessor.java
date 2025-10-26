package com.pigx.engine.core.foundation.support.crypto;

import com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor;
import cn.hutool.v7.core.codec.binary.Base64;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.util.ByteUtil;
import cn.hutool.v7.core.util.RandomUtil;
import cn.hutool.v7.crypto.SecureUtil;
import cn.hutool.v7.crypto.symmetric.AES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/support/crypto/AESCryptoProcessor.class */
public class AESCryptoProcessor implements SymmetricCryptoProcessor {
    private static final Logger log = LoggerFactory.getLogger(AESCryptoProcessor.class);

    @Override // com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor
    public String createKey() {
        return RandomUtil.randomStringUpper(16);
    }

    @Override // com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor
    public String decrypt(String data, String key) {
        AES aes = SecureUtil.aes(ByteUtil.toUtf8Bytes(key));
        byte[] result = aes.decrypt(Base64.decode(ByteUtil.toUtf8Bytes(data)));
        log.trace("[Herodotus] |- AES crypto decrypt data, value is : [{}]", result);
        return StrUtil.utf8Str(result);
    }

    @Override // com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor
    public String encrypt(String data, String key) {
        AES aes = SecureUtil.aes(ByteUtil.toUtf8Bytes(key));
        byte[] result = aes.encrypt(ByteUtil.toUtf8Bytes(data));
        log.trace("[Herodotus] |- AES crypto encrypt data, value is : [{}]", result);
        return StrUtil.utf8Str(result);
    }
}
