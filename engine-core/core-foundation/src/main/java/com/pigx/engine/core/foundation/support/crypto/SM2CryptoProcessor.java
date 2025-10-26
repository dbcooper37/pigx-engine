package com.pigx.engine.core.foundation.support.crypto;

import com.pigx.engine.core.definition.domain.SecretKey;
import com.pigx.engine.core.definition.support.crypto.AsymmetricCryptoProcessor;
import cn.hutool.v7.core.codec.binary.HexUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.crypto.asymmetric.KeyType;
import cn.hutool.v7.crypto.asymmetric.SM2;
import cn.hutool.v7.crypto.bc.ECKeyUtil;
import cn.hutool.v7.crypto.bc.SmUtil;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SM2CryptoProcessor implements AsymmetricCryptoProcessor {
    private static final Logger log = LoggerFactory.getLogger(SM2CryptoProcessor.class);

    public SecretKey createSecretKey() {
        SM2 sm2 = SmUtil.sm2();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        String privateKey = HexUtil.encodeStr(ECKeyUtil.encodeECPrivateKey(sm2.getPrivateKey()));
        String publicKey = HexUtil.encodeStr(((BCECPublicKey)sm2.getPublicKey()).getQ().getEncoded(false));
        SecretKey secretKey = new SecretKey();
        secretKey.setPrivateKey(privateKey);
        secretKey.setPublicKey(publicKey);
        return secretKey;
    }

    public String decrypt(String content, String privateKey) {
        SM2 sm2 = SmUtil.sm2(privateKey, (String)null);
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        String result = StrUtil.utf8Str(sm2.decrypt(content, KeyType.PrivateKey));
        log.trace("[Herodotus] |- SM2 crypto decrypt data, value is : [{}]", result);
        return result;
    }

    public String encrypt(String content, String publicKey) {
        SM2 sm2 = SmUtil.sm2((String)null, publicKey);
        String result = sm2.encryptHex(content, KeyType.PublicKey);
        log.trace("[Herodotus] |- SM2 crypto encrypt data, value is : [{}]", result);
        return result;
    }
}
