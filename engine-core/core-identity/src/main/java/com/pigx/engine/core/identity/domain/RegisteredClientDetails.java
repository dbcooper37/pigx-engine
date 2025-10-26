package com.pigx.engine.core.identity.domain;

import java.time.LocalDateTime;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/domain/RegisteredClientDetails.class */
public interface RegisteredClientDetails {
    String getId();

    String getClientId();

    LocalDateTime getClientIdIssuedAt();

    String getClientSecret();

    LocalDateTime getClientSecretExpiresAt();

    String getClientAuthenticationMethods();

    String getAuthorizationGrantTypes();

    String getRedirectUris();

    String getPostLogoutRedirectUris();
}
