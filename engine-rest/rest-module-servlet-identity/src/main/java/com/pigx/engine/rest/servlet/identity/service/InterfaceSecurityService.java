package com.pigx.engine.rest.servlet.identity.service;

import com.pigx.engine.core.definition.domain.SecretKey;
import com.pigx.engine.core.identity.constant.OAuth2ErrorKeys;
import com.pigx.engine.core.identity.utils.SecurityUtils;
import com.pigx.engine.web.servlet.crypto.HttpCryptoProcessor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/service/InterfaceSecurityService.class */
public class InterfaceSecurityService {
    private final HttpCryptoProcessor httpCryptoProcessor;
    private final RegisteredClientRepository registeredClientRepository;

    public InterfaceSecurityService(HttpCryptoProcessor httpCryptoProcessor, RegisteredClientRepository registeredClientRepository) {
        this.httpCryptoProcessor = httpCryptoProcessor;
        this.registeredClientRepository = registeredClientRepository;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.springframework.security.oauth2.core.OAuth2AuthenticationException */
    private RegisteredClient validateClient(String clientId, String clientSecret) throws OAuth2AuthenticationException {
        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(clientId);
        boolean isMatch = false;
        if (ObjectUtils.isNotEmpty(registeredClient)) {
            isMatch = SecurityUtils.matches(clientSecret, registeredClient.getClientSecret());
        }
        if (!isMatch) {
            throw new OAuth2AuthenticationException(OAuth2ErrorKeys.INVALID_CLIENT);
        }
        return registeredClient;
    }

    public SecretKey createSecretKey(String clientId, String clientSecret, String sessionId) throws OAuth2AuthenticationException {
        RegisteredClient registeredClient = validateClient(clientId, clientSecret);
        return this.httpCryptoProcessor.createSecretKey(sessionId, registeredClient.getTokenSettings().getAccessTokenTimeToLive());
    }

    public String exchange(String sessionId, String confidentialBase64) {
        return this.httpCryptoProcessor.exchange(sessionId, confidentialBase64);
    }
}
