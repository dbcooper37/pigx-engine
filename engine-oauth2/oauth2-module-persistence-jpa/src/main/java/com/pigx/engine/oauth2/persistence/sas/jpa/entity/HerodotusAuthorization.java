package com.pigx.engine.oauth2.persistence.sas.jpa.entity;

import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import com.pigx.engine.oauth2.persistence.sas.jpa.generator.HerodotusAuthorizationIdGenerator;
import com.google.common.base.MoreObjects;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_AUTHORIZATION)
@Cacheable
@Entity
@Table(name = "oauth2_authorization", indexes = {@Index(name = "oauth2_authorization_id_idx", columnList = "id"), @Index(name = "oauth2_authorization_rci_idx", columnList = "registered_client_id"), @Index(name = "oauth2_authorization_pn_idx", columnList = "principal_name")})
/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/entity/HerodotusAuthorization.class */
public class HerodotusAuthorization implements BaseEntity {

    @Id
    @HerodotusAuthorizationIdGenerator
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @Column(name = "registered_client_id", nullable = false, length = 100)
    private String registeredClientId;

    @Column(name = "principal_name", nullable = false, length = 200)
    private String principalName;

    @Column(name = "authorization_grant_type", nullable = false, length = 100)
    private String authorizationGrantType;

    @Column(name = "authorized_scopes", length = 1000)
    private String authorizedScopes;

    @Column(name = "attributes", columnDefinition = "TEXT")
    private String attributes;

    @Column(name = "state", length = 500)
    private String state;

    @Column(name = "authorization_code_value", columnDefinition = "TEXT")
    private String authorizationCodeValue;

    @Column(name = "authorization_code_issued_at")
    private LocalDateTime authorizationCodeIssuedAt;

    @Column(name = "authorization_code_expires_at")
    private LocalDateTime authorizationCodeExpiresAt;

    @Column(name = "authorization_code_metadata", columnDefinition = "TEXT")
    private String authorizationCodeMetadata;

    @Column(name = "access_token_value", columnDefinition = "TEXT")
    private String accessTokenValue;

    @Column(name = "access_token_issued_at")
    private LocalDateTime accessTokenIssuedAt;

    @Column(name = "access_token_expires_at")
    private LocalDateTime accessTokenExpiresAt;

    @Column(name = "access_token_metadata", columnDefinition = "TEXT")
    private String accessTokenMetadata;

    @Column(name = "access_token_type", length = 100)
    private String accessTokenType;

    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    @Column(name = "oidc_id_token_value", columnDefinition = "TEXT")
    private String oidcIdTokenValue;

    @Column(name = "oidc_id_token_issued_at")
    private LocalDateTime oidcIdTokenIssuedAt;

    @Column(name = "oidc_id_token_expires_at")
    private LocalDateTime oidcIdTokenExpiresAt;

    @Column(name = "oidc_id_token_metadata", columnDefinition = "TEXT")
    private String oidcIdTokenMetadata;

    @Column(name = "oidc_id_token_claims", length = 2000)
    private String oidcIdTokenClaims;

    @Column(name = "refresh_token_value", columnDefinition = "TEXT")
    private String refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private LocalDateTime refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at")
    private LocalDateTime refreshTokenExpiresAt;

    @Column(name = "refresh_token_metadata", columnDefinition = "TEXT")
    private String refreshTokenMetadata;

    @Column(name = "user_code_value", columnDefinition = "TEXT")
    private String userCodeValue;

    @Column(name = "user_code_issued_at")
    private LocalDateTime userCodeIssuedAt;

    @Column(name = "user_code_expires_at")
    private LocalDateTime userCodeExpiresAt;

    @Column(name = "user_code_metadata", columnDefinition = "TEXT")
    private String userCodeMetadata;

    @Column(name = "device_code_value", columnDefinition = "TEXT")
    private String deviceCodeValue;

    @Column(name = "device_code_issued_at")
    private LocalDateTime deviceCodeIssuedAt;

    @Column(name = "device_code_expires_at")
    private LocalDateTime deviceCodeExpiresAt;

    @Column(name = "device_code_metadata", columnDefinition = "TEXT")
    private String deviceCodeMetadata;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisteredClientId() {
        return this.registeredClientId;
    }

    public void setRegisteredClientId(String registeredClientId) {
        this.registeredClientId = registeredClientId;
    }

    public String getPrincipalName() {
        return this.principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getAuthorizationGrantType() {
        return this.authorizationGrantType;
    }

    public void setAuthorizationGrantType(String authorizationGrantType) {
        this.authorizationGrantType = authorizationGrantType;
    }

    public String getAuthorizedScopes() {
        return this.authorizedScopes;
    }

    public void setAuthorizedScopes(String authorizedScopes) {
        this.authorizedScopes = authorizedScopes;
    }

    public String getAttributes() {
        return this.attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAuthorizationCodeValue() {
        return this.authorizationCodeValue;
    }

    public void setAuthorizationCodeValue(String authorizationCodeValue) {
        this.authorizationCodeValue = authorizationCodeValue;
    }

    public LocalDateTime getAuthorizationCodeIssuedAt() {
        return this.authorizationCodeIssuedAt;
    }

    public void setAuthorizationCodeIssuedAt(LocalDateTime authorizationCodeIssuedAt) {
        this.authorizationCodeIssuedAt = authorizationCodeIssuedAt;
    }

    public LocalDateTime getAuthorizationCodeExpiresAt() {
        return this.authorizationCodeExpiresAt;
    }

    public void setAuthorizationCodeExpiresAt(LocalDateTime authorizationCodeExpiresAt) {
        this.authorizationCodeExpiresAt = authorizationCodeExpiresAt;
    }

    public String getAuthorizationCodeMetadata() {
        return this.authorizationCodeMetadata;
    }

    public void setAuthorizationCodeMetadata(String authorizationCodeMetadata) {
        this.authorizationCodeMetadata = authorizationCodeMetadata;
    }

    public String getAccessTokenValue() {
        return this.accessTokenValue;
    }

    public void setAccessTokenValue(String accessTokenValue) {
        this.accessTokenValue = accessTokenValue;
    }

    public LocalDateTime getAccessTokenIssuedAt() {
        return this.accessTokenIssuedAt;
    }

    public void setAccessTokenIssuedAt(LocalDateTime accessTokenIssuedAt) {
        this.accessTokenIssuedAt = accessTokenIssuedAt;
    }

    public LocalDateTime getAccessTokenExpiresAt() {
        return this.accessTokenExpiresAt;
    }

    public void setAccessTokenExpiresAt(LocalDateTime accessTokenExpiresAt) {
        this.accessTokenExpiresAt = accessTokenExpiresAt;
    }

    public String getAccessTokenMetadata() {
        return this.accessTokenMetadata;
    }

    public void setAccessTokenMetadata(String accessTokenMetadata) {
        this.accessTokenMetadata = accessTokenMetadata;
    }

    public String getAccessTokenType() {
        return this.accessTokenType;
    }

    public void setAccessTokenType(String accessTokenType) {
        this.accessTokenType = accessTokenType;
    }

    public String getAccessTokenScopes() {
        return this.accessTokenScopes;
    }

    public void setAccessTokenScopes(String accessTokenScopes) {
        this.accessTokenScopes = accessTokenScopes;
    }

    public String getOidcIdTokenValue() {
        return this.oidcIdTokenValue;
    }

    public void setOidcIdTokenValue(String oidcIdTokenValue) {
        this.oidcIdTokenValue = oidcIdTokenValue;
    }

    public LocalDateTime getOidcIdTokenIssuedAt() {
        return this.oidcIdTokenIssuedAt;
    }

    public void setOidcIdTokenIssuedAt(LocalDateTime oidcIdTokenIssuedAt) {
        this.oidcIdTokenIssuedAt = oidcIdTokenIssuedAt;
    }

    public LocalDateTime getOidcIdTokenExpiresAt() {
        return this.oidcIdTokenExpiresAt;
    }

    public void setOidcIdTokenExpiresAt(LocalDateTime oidcIdTokenExpiresAt) {
        this.oidcIdTokenExpiresAt = oidcIdTokenExpiresAt;
    }

    public String getOidcIdTokenMetadata() {
        return this.oidcIdTokenMetadata;
    }

    public void setOidcIdTokenMetadata(String oidcIdTokenMetadata) {
        this.oidcIdTokenMetadata = oidcIdTokenMetadata;
    }

    public String getOidcIdTokenClaims() {
        return this.oidcIdTokenClaims;
    }

    public void setOidcIdTokenClaims(String oidcIdTokenClaims) {
        this.oidcIdTokenClaims = oidcIdTokenClaims;
    }

    public String getRefreshTokenValue() {
        return this.refreshTokenValue;
    }

    public void setRefreshTokenValue(String refreshTokenValue) {
        this.refreshTokenValue = refreshTokenValue;
    }

    public LocalDateTime getRefreshTokenIssuedAt() {
        return this.refreshTokenIssuedAt;
    }

    public void setRefreshTokenIssuedAt(LocalDateTime refreshTokenIssuedAt) {
        this.refreshTokenIssuedAt = refreshTokenIssuedAt;
    }

    public LocalDateTime getRefreshTokenExpiresAt() {
        return this.refreshTokenExpiresAt;
    }

    public void setRefreshTokenExpiresAt(LocalDateTime refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public String getRefreshTokenMetadata() {
        return this.refreshTokenMetadata;
    }

    public void setRefreshTokenMetadata(String refreshTokenMetadata) {
        this.refreshTokenMetadata = refreshTokenMetadata;
    }

    public String getUserCodeValue() {
        return this.userCodeValue;
    }

    public void setUserCodeValue(String userCodeValue) {
        this.userCodeValue = userCodeValue;
    }

    public LocalDateTime getUserCodeIssuedAt() {
        return this.userCodeIssuedAt;
    }

    public void setUserCodeIssuedAt(LocalDateTime userCodeIssuedAt) {
        this.userCodeIssuedAt = userCodeIssuedAt;
    }

    public LocalDateTime getUserCodeExpiresAt() {
        return this.userCodeExpiresAt;
    }

    public void setUserCodeExpiresAt(LocalDateTime userCodeExpiresAt) {
        this.userCodeExpiresAt = userCodeExpiresAt;
    }

    public String getUserCodeMetadata() {
        return this.userCodeMetadata;
    }

    public void setUserCodeMetadata(String userCodeMetadata) {
        this.userCodeMetadata = userCodeMetadata;
    }

    public String getDeviceCodeValue() {
        return this.deviceCodeValue;
    }

    public void setDeviceCodeValue(String deviceCodeValue) {
        this.deviceCodeValue = deviceCodeValue;
    }

    public LocalDateTime getDeviceCodeIssuedAt() {
        return this.deviceCodeIssuedAt;
    }

    public void setDeviceCodeIssuedAt(LocalDateTime deviceCodeIssuedAt) {
        this.deviceCodeIssuedAt = deviceCodeIssuedAt;
    }

    public LocalDateTime getDeviceCodeExpiresAt() {
        return this.deviceCodeExpiresAt;
    }

    public void setDeviceCodeExpiresAt(LocalDateTime deviceCodeExpiresAt) {
        this.deviceCodeExpiresAt = deviceCodeExpiresAt;
    }

    public String getDeviceCodeMetadata() {
        return this.deviceCodeMetadata;
    }

    public void setDeviceCodeMetadata(String deviceCodeMetadata) {
        this.deviceCodeMetadata = deviceCodeMetadata;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", this.id).add("registeredClientId", this.registeredClientId).add("principalName", this.principalName).add("attributes", this.attributes).add("state", this.state).toString();
    }
}
