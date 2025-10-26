package com.pigx.engine.core.foundation.support.crypto;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.domain.SecretKey;
import com.pigx.engine.core.definition.support.crypto.AsymmetricCryptoProcessor;
import cn.hutool.v7.core.codec.binary.Base64;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.crypto.SecureUtil;
import cn.hutool.v7.crypto.asymmetric.KeyType;
import cn.hutool.v7.crypto.asymmetric.RSA;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/support/crypto/RSACryptoProcessor.class */
public class RSACryptoProcessor implements AsymmetricCryptoProcessor {
    private static final Logger log = LoggerFactory.getLogger(RSACryptoProcessor.class);
    private static final String PKCS8_PUBLIC_KEY_BEGIN = "-----BEGIN PUBLIC KEY-----";
    private static final String PKCS8_PUBLIC_KEY_END = "-----END PUBLIC KEY-----";

    @Override // com.pigx.engine.core.definition.support.crypto.AsymmetricCryptoProcessor
    public SecretKey createSecretKey() {
        RSA rsa = SecureUtil.rsa();
        SecretKey secretKey = new SecretKey();
        secretKey.setPrivateKey(rsa.getPrivateKeyBase64());
        secretKey.setPublicKey(appendPkcs8Padding(rsa.getPublicKeyBase64()));
        return secretKey;
    }

    private String removePkcs8Padding(String key) {
        String result = Strings.CS.replace(key, SymbolConstants.NEW_LINE, SymbolConstants.BLANK);
        String[] values = StringUtils.split(result, "-----");
        if (ArrayUtils.isNotEmpty(values)) {
            return values[1];
        }
        return key;
    }

    public String appendPkcs8Padding(String key) {
        return "-----BEGIN PUBLIC KEY-----\n" + key + "\n-----END PUBLIC KEY-----";
    }

    @Override // com.pigx.engine.core.definition.support.crypto.AsymmetricCryptoProcessor
    public String decrypt(String content, String privateKey) {
        byte[] base64Data = Base64.decode(content);
        RSA rsa = SecureUtil.rsa(privateKey, (String) null);
        String result = StrUtil.utf8Str(rsa.decrypt(base64Data, KeyType.PrivateKey));
        log.trace("[Herodotus] |- RSA crypto decrypt data, value is : [{}]", result);
        return result;
    }

    @Override // com.pigx.engine.core.definition.support.crypto.AsymmetricCryptoProcessor
    public String encrypt(String content, String publicKey) {
        String key = removePkcs8Padding(publicKey);
        RSA rsa = SecureUtil.rsa((String) null, key);
        byte[] encryptedData = rsa.encrypt(content, KeyType.PublicKey);
        String result = Base64.encode(encryptedData);
        log.trace("[Herodotus] |- RSA crypto decrypt data, value is : [{}]", result);
        return result;
    }
}
