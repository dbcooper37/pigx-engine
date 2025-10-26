package com.pigx.engine.logic.identity.definition;

import com.pigx.engine.logic.identity.entity.OAuth2Scope;
import com.pigx.engine.oauth2.core.enums.AllJwsAlgorithm;
import com.pigx.engine.oauth2.core.enums.SignatureJwsAlgorithm;
import com.pigx.engine.oauth2.core.enums.TokenFormat;
import com.pigx.engine.oauth2.persistence.sas.jpa.definition.AbstractRegisteredClient;
import cn.hutool.v7.core.data.id.IdUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import java.time.Duration;
import java.util.Set;

@MappedSuperclass
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/definition/AbstractOAuth2RegisteredClient.class */
public abstract class AbstractOAuth2RegisteredClient extends AbstractRegisteredClient {

    @Column(name = "jwk_set_url", length = 1000)
    @Schema(name = "客户端JSON Web密钥集的URL", title = "客户端JSON Web密钥集的URL")
    private String jwkSetUrl;

    @Column(name = "signing_algorithm")
    @Enumerated(EnumType.ORDINAL)
    @Schema(name = "JWT 签名算法", title = "仅在 clientAuthenticationMethods 为 private_key_jwt 和 client_secret_jwt 方法下使用")
    private AllJwsAlgorithm authenticationSigningAlgorithm;

    @Column(name = "subject_dn")
    @Schema(name = "X509证书DN")
    private String x509CertificateSubjectDN;

    @Column(name = "client_id", length = 100)
    @Schema(name = "客户端Id", title = "默认为系统自动生成")
    private String clientId = IdUtil.fastSimpleUUID();

    @Column(name = "client_secret", length = 100)
    @Schema(name = "客户端秘钥", title = "这里存储的客户端秘钥是明文，方便使用。默认为系统自动生成")
    private String clientSecret = IdUtil.fastSimpleUUID();

    @Column(name = "require_proof_key")
    @Schema(name = "是否需要证明Key", title = "如果客户端在执行授权码授予流时需要提供验证密钥质询和验证器, 默认False")
    private Boolean requireProofKey = Boolean.FALSE;

    @Column(name = "require_authorization_consent")
    @Schema(name = "是否需要认证确认", title = "如果客户端在执行授权码授予流时需要提供验证密钥质询和验证器, 默认False")
    private Boolean requireAuthorizationConsent = Boolean.TRUE;

    @Column(name = "authorization_code_validity")
    @Schema(name = "授权码有效时间", title = "默认5分钟，使用 Duration 时间格式")
    private Duration authorizationCodeValidity = Duration.ofMinutes(5);

    @Column(name = "device_code_validity")
    @Schema(name = "激活码有效时间", title = "默认5分钟，使用 Duration 时间格式")
    private Duration deviceCodeValidity = Duration.ofMinutes(5);

    @Column(name = "access_token_validity")
    @Schema(name = "AccessToken 有效时间", title = "默认5分钟，使用 Duration 时间格式")
    private Duration accessTokenValidity = Duration.ofMinutes(5);

    @Column(name = "refresh_token_validity")
    @Schema(name = "RefreshToken 有效时间", title = "默认60分钟，使用 Duration 时间格式")
    private Duration refreshTokenValidity = Duration.ofMinutes(60);

    @Column(name = "access_token_format")
    @Enumerated(EnumType.ORDINAL)
    @Schema(name = "Access Token 格式", title = "OAuth 2.0令牌的标准数据格式")
    private TokenFormat accessTokenFormat = TokenFormat.REFERENCE;

    @Column(name = "reuse_refresh_tokens")
    @Schema(name = "是否重用 Refresh Token", title = "默认值 True")
    private Boolean reuseRefreshTokens = Boolean.TRUE;

    @Column(name = "signature_algorithm")
    @Enumerated(EnumType.ORDINAL)
    @Schema(name = "IdToken 签名算法", title = "JWT 算法用于签名 ID Token， 默认值 RS256")
    private SignatureJwsAlgorithm idTokenSignatureAlgorithmJwsAlgorithm = SignatureJwsAlgorithm.RS256;

    @Column(name = "bound_access_token")
    @Schema(name = "X509证书是否绑定 AccessToken", title = "默认值 false")
    private Boolean x509CertificateBoundAccessTokens = Boolean.FALSE;

    public abstract Set<OAuth2Scope> getScopes();

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public String getClientSecret() {
        return this.clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Boolean getRequireProofKey() {
        return this.requireProofKey;
    }

    public void setRequireProofKey(Boolean requireProofKey) {
        this.requireProofKey = requireProofKey;
    }

    public Boolean getRequireAuthorizationConsent() {
        return this.requireAuthorizationConsent;
    }

    public void setRequireAuthorizationConsent(Boolean requireAuthorizationConsent) {
        this.requireAuthorizationConsent = requireAuthorizationConsent;
    }

    public String getJwkSetUrl() {
        return this.jwkSetUrl;
    }

    public void setJwkSetUrl(String jwkSetUrl) {
        this.jwkSetUrl = jwkSetUrl;
    }

    public AllJwsAlgorithm getAuthenticationSigningAlgorithm() {
        return this.authenticationSigningAlgorithm;
    }

    public void setAuthenticationSigningAlgorithm(AllJwsAlgorithm authenticationSigningAlgorithm) {
        this.authenticationSigningAlgorithm = authenticationSigningAlgorithm;
    }

    public Duration getAuthorizationCodeValidity() {
        return this.authorizationCodeValidity;
    }

    public void setAuthorizationCodeValidity(Duration authorizationCodeValidity) {
        this.authorizationCodeValidity = authorizationCodeValidity;
    }

    public Duration getAccessTokenValidity() {
        return this.accessTokenValidity;
    }

    public void setAccessTokenValidity(Duration accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Duration getDeviceCodeValidity() {
        return this.deviceCodeValidity;
    }

    public void setDeviceCodeValidity(Duration deviceCodeValidity) {
        this.deviceCodeValidity = deviceCodeValidity;
    }

    public Duration getRefreshTokenValidity() {
        return this.refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Duration refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public TokenFormat getAccessTokenFormat() {
        return this.accessTokenFormat;
    }

    public void setAccessTokenFormat(TokenFormat accessTokenFormat) {
        this.accessTokenFormat = accessTokenFormat;
    }

    public Boolean getReuseRefreshTokens() {
        return this.reuseRefreshTokens;
    }

    public void setReuseRefreshTokens(Boolean reuseRefreshTokens) {
        this.reuseRefreshTokens = reuseRefreshTokens;
    }

    public SignatureJwsAlgorithm getIdTokenSignatureAlgorithm() {
        return this.idTokenSignatureAlgorithmJwsAlgorithm;
    }

    public void setIdTokenSignatureAlgorithm(SignatureJwsAlgorithm idTokenSignatureAlgorithmJwsAlgorithm) {
        this.idTokenSignatureAlgorithmJwsAlgorithm = idTokenSignatureAlgorithmJwsAlgorithm;
    }

    public String getX509CertificateSubjectDN() {
        return this.x509CertificateSubjectDN;
    }

    public void setX509CertificateSubjectDN(String x509CertificateSubjectDN) {
        this.x509CertificateSubjectDN = x509CertificateSubjectDN;
    }

    public SignatureJwsAlgorithm getIdTokenSignatureAlgorithmJwsAlgorithm() {
        return this.idTokenSignatureAlgorithmJwsAlgorithm;
    }

    public void setIdTokenSignatureAlgorithmJwsAlgorithm(SignatureJwsAlgorithm idTokenSignatureAlgorithmJwsAlgorithm) {
        this.idTokenSignatureAlgorithmJwsAlgorithm = idTokenSignatureAlgorithmJwsAlgorithm;
    }

    public Boolean getX509CertificateBoundAccessTokens() {
        return this.x509CertificateBoundAccessTokens;
    }

    public void setX509CertificateBoundAccessTokens(Boolean x509CertificateBoundAccessTokens) {
        this.x509CertificateBoundAccessTokens = x509CertificateBoundAccessTokens;
    }
}
