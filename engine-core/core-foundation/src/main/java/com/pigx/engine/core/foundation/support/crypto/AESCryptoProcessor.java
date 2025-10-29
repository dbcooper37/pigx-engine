package com.pigx.engine.core.foundation.support.crypto;

import cn.hutool.v7.core.codec.binary.Base64;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.util.ByteUtil;
import cn.hutool.v7.core.util.RandomUtil;
import cn.hutool.v7.crypto.SecureUtil;
import cn.hutool.v7.crypto.symmetric.AES;
import com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AESCryptoProcessor implements SymmetricCryptoProcessor {

    private static final Logger log = LoggerFactory.getLogger(AESCryptoProcessor.class);

    @Override
    public String createKey() {
        return RandomUtil.randomStringUpper(16);
    }

    @Override
    public String decrypt(String data, String key) {
        AES aes = SecureUtil.aes(ByteUtil.toUtf8Bytes(key));
        byte[] result = aes.decrypt(Base64.decode(ByteUtil.toUtf8Bytes(data)));
        log.trace("[PIGXD] |- AES crypto decrypt data, value is : [{}]", result);
        return StrUtil.utf8Str(result);
    }

    @Override
    public String encrypt(String data, String key) {
        AES aes = SecureUtil.aes(ByteUtil.toUtf8Bytes(key));
        byte[] result = aes.encrypt(ByteUtil.toUtf8Bytes(data));
        log.trace("[PIGXD] |- AES crypto encrypt data, value is : [{}]", result);
        return StrUtil.utf8Str(result);
    }
}
