package com.pigx.engine.web.servlet.crypto;

import com.pigx.engine.cache.core.exception.StampHasExpiredException;
import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import com.pigx.engine.core.definition.domain.SecretKey;
import com.pigx.engine.core.definition.support.crypto.AsymmetricCryptoProcessor;
import com.pigx.engine.core.definition.support.crypto.SymmetricCryptoProcessor;
import com.pigx.engine.web.core.constant.WebConstants;
import com.pigx.engine.web.core.exception.SessionInvalidException;
import cn.hutool.v7.core.data.id.IdUtil;
import java.time.Duration;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;

/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/crypto/HttpCryptoProcessor.class */
public class HttpCryptoProcessor extends AbstractStampManager<String, SecretKey> {
    private static final Logger log = LoggerFactory.getLogger(HttpCryptoProcessor.class);
    private final AsymmetricCryptoProcessor asymmetricCryptoProcessor;
    private final SymmetricCryptoProcessor symmetricCryptoProcessor;

    public HttpCryptoProcessor(AsymmetricCryptoProcessor asymmetricCryptoProcessor, SymmetricCryptoProcessor symmetricCryptoProcessor, ServerProperties serverProperties) {
        super(WebConstants.CACHE_NAME_TOKEN_SECURE_KEY, serverProperties.getServlet().getSession().getTimeout());
        this.asymmetricCryptoProcessor = asymmetricCryptoProcessor;
        this.symmetricCryptoProcessor = symmetricCryptoProcessor;
    }

    public String encrypt(String identity, String content) {
        try {
            SecretKey secretKey = getSecretKey(identity);
            String result = this.symmetricCryptoProcessor.encrypt(content, secretKey.getSymmetricKey());
            log.debug("[Herodotus] |- Encrypt content from [{}] to [{}].", content, result);
            return result;
        } catch (StampHasExpiredException e) {
            log.warn("[Herodotus] |- Session has expired, need recreate, Skip encrypt content [{}].", content);
            return content;
        } catch (Exception e2) {
            log.warn("[Herodotus] |- Symmetric can not Encrypt content [{}], Skip!", content);
            return content;
        }
    }

    public String decrypt(String identity, String content) {
        try {
            SecretKey secretKey = getSecretKey(identity);
            String result = this.symmetricCryptoProcessor.decrypt(content, secretKey.getSymmetricKey());
            log.debug("[Herodotus] |- Decrypt content from [{}] to [{}].", content, result);
            return result;
        } catch (StampHasExpiredException e) {
            log.warn("[Herodotus] |- Session has expired, need recreate, Skip decrypt content [{}].", content);
            return content;
        } catch (Exception e2) {
            log.warn("[Herodotus] |- Symmetric can not Decrypt content [{}], Skip!", content);
            return content;
        }
    }

    public SecretKey createSecretKey(String identity, Duration accessTokenValiditySeconds) {
        if (StringUtils.isBlank(identity)) {
            identity = IdUtil.fastUUID();
        } else {
            try {
                return getSecretKey(identity);
            } catch (StampHasExpiredException e) {
                log.debug("[Herodotus] |- SecretKey has expired, recreate it");
            }
        }
        Duration expire = getExpire(accessTokenValiditySeconds);
        return create(identity, expire);
    }

    @Override // com.pigx.engine.cache.jetcache.stamp.StampManager
    public SecretKey nextStamp(String key) {
        SecretKey secretKey = this.asymmetricCryptoProcessor.createSecretKey();
        String symmetricKey = this.symmetricCryptoProcessor.createKey();
        secretKey.setSymmetricKey(symmetricKey);
        secretKey.setIdentity(key);
        secretKey.setState(IdUtil.fastUUID());
        log.debug("[Herodotus] |- Generate secret key, value is : [{}]", secretKey);
        return secretKey;
    }

    private boolean isSessionValid(String identity) {
        return containKey(identity);
    }

    private SecretKey getSecretKey(String identity) throws StampHasExpiredException {
        if (isSessionValid(identity)) {
            SecretKey secretKey = get(identity);
            if (ObjectUtils.isNotEmpty(secretKey)) {
                log.trace("[Herodotus] |- Decrypt Or Encrypt content use param identity [{}], cached identity is [{}].", identity, secretKey.getIdentity());
                return secretKey;
            }
        }
        throw new StampHasExpiredException("SecretKey key is expired!");
    }

    private Duration getExpire(Duration accessTokenValiditySeconds) {
        if (ObjectUtils.isEmpty(accessTokenValiditySeconds) || accessTokenValiditySeconds.isZero()) {
            return Duration.ofHours(2L);
        }
        return accessTokenValiditySeconds;
    }

    private String decryptFrontendPublicKey(String content, String privateKey) {
        String frontendPublicKey = this.asymmetricCryptoProcessor.decrypt(content, privateKey);
        log.debug("[Herodotus] |- Decrypt frontend public key, value is : [{}]", frontendPublicKey);
        return frontendPublicKey;
    }

    private String encryptBackendKey(String symmetricKey, String publicKey) {
        String encryptedAesKey = this.asymmetricCryptoProcessor.encrypt(symmetricKey, publicKey);
        log.debug("[Herodotus] |- Encrypt symmetric key use frontend public key, value is : [{}]", encryptedAesKey);
        return encryptedAesKey;
    }

    public String exchange(String identity, String confidential) {
        try {
            SecretKey secretKey = getSecretKey(identity);
            String frontendPublicKey = decryptFrontendPublicKey(confidential, secretKey.getPrivateKey());
            return encryptBackendKey(secretKey.getSymmetricKey(), frontendPublicKey);
        } catch (StampHasExpiredException e) {
            throw new SessionInvalidException();
        }
    }
}
