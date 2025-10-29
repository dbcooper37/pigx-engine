package com.pigx.engine.core.identity.domain;

import java.time.LocalDateTime;


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
