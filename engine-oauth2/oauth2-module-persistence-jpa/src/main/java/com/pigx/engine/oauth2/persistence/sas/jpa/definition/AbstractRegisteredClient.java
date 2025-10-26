package com.pigx.engine.oauth2.persistence.sas.jpa.definition;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.foundation.jackson2.ArrayToCommaDelimitedStringDeserializer;
import com.pigx.engine.core.foundation.jackson2.CommaDelimitedStringToSetSerializer;
import com.pigx.engine.core.identity.domain.RegisteredClientDetails;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@MappedSuperclass
/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/definition/AbstractRegisteredClient.class */
public abstract class AbstractRegisteredClient extends AbstractSysEntity implements RegisteredClientDetails {

    @Schema(name = "客户端ID发布日期", title = "客户端发布日期")
    @Column(name = "client_id_issued_at", nullable = false, updatable = false)
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT, locale = "GMT+8", shape = JsonFormat.Shape.STRING)
    @CreationTimestamp
    private LocalDateTime clientIdIssuedAt;

    @Column(name = "client_secret_expires_at")
    @Schema(name = "客户端秘钥过期时间", title = "客户端秘钥过期时间")
    @JsonFormat(pattern = SystemConstants.DATE_TIME_FORMAT, locale = "GMT+8", shape = JsonFormat.Shape.STRING)
    private LocalDateTime clientSecretExpiresAt;

    @Schema(name = "客户端认证模式", title = "支持多个值，以逗号分隔", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = ArrayToCommaDelimitedStringDeserializer.class)
    @JsonSerialize(using = CommaDelimitedStringToSetSerializer.class)
    @Column(name = "client_authentication_methods", nullable = false, length = 1000)
    private String clientAuthenticationMethods;

    @Schema(name = "认证模式", title = "支持多个值，以逗号分隔", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonDeserialize(using = ArrayToCommaDelimitedStringDeserializer.class)
    @JsonSerialize(using = CommaDelimitedStringToSetSerializer.class)
    @Column(name = "authorization_grant_types", nullable = false, length = 1000)
    private String authorizationGrantTypes;

    @Column(name = "redirect_uris", length = 1000)
    @Schema(name = "回调地址", title = "支持多个值，以逗号分隔")
    private String redirectUris;

    @Column(name = "post_logout_redirect_uris", length = 1000)
    @Schema(name = "OIDC Logout 回调地址", title = "支持多个值，以逗号分隔")
    private String postLogoutRedirectUris;

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public LocalDateTime getClientIdIssuedAt() {
        return this.clientIdIssuedAt;
    }

    public void setClientIdIssuedAt(LocalDateTime clientIdIssuedAt) {
        this.clientIdIssuedAt = clientIdIssuedAt;
    }

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public LocalDateTime getClientSecretExpiresAt() {
        return this.clientSecretExpiresAt;
    }

    public void setClientSecretExpiresAt(LocalDateTime clientSecretExpiresAt) {
        this.clientSecretExpiresAt = clientSecretExpiresAt;
    }

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public String getClientAuthenticationMethods() {
        return this.clientAuthenticationMethods;
    }

    public void setClientAuthenticationMethods(String clientAuthenticationMethods) {
        this.clientAuthenticationMethods = clientAuthenticationMethods;
    }

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public String getAuthorizationGrantTypes() {
        return this.authorizationGrantTypes;
    }

    public void setAuthorizationGrantTypes(String authorizationGrantTypes) {
        this.authorizationGrantTypes = authorizationGrantTypes;
    }

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public String getRedirectUris() {
        return this.redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public String getPostLogoutRedirectUris() {
        return this.postLogoutRedirectUris;
    }

    public void setPostLogoutRedirectUris(String postLogoutRedirectUris) {
        this.postLogoutRedirectUris = postLogoutRedirectUris;
    }
}
