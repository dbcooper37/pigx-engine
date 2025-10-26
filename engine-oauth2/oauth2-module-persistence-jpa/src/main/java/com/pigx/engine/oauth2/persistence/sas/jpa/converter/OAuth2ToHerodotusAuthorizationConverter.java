package com.pigx.engine.oauth2.persistence.sas.jpa.converter;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.oauth2.persistence.sas.jpa.definition.AbstractOAuth2EntityConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorization;
import com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor;
import cn.hutool.v7.core.date.DateUtil;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Consumer;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/converter/OAuth2ToHerodotusAuthorizationConverter.class */
public class OAuth2ToHerodotusAuthorizationConverter extends AbstractOAuth2EntityConverter<OAuth2Authorization, HerodotusAuthorization> {
    public OAuth2ToHerodotusAuthorizationConverter(OAuth2JacksonProcessor jacksonProcessor) {
        super(jacksonProcessor);
    }

    public HerodotusAuthorization convert(OAuth2Authorization authorization) {
        HerodotusAuthorization entity = new HerodotusAuthorization();
        entity.setId(authorization.getId());
        entity.setRegisteredClientId(authorization.getRegisteredClientId());
        entity.setPrincipalName(authorization.getPrincipalName());
        entity.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        entity.setAuthorizedScopes(StringUtils.collectionToDelimitedString(authorization.getAuthorizedScopes(), SymbolConstants.COMMA));
        entity.setAttributes(writeMap(authorization.getAttributes()));
        entity.setState((String) authorization.getAttribute("state"));
        OAuth2Authorization.Token<?> token = authorization.getToken(OAuth2AuthorizationCode.class);
        Objects.requireNonNull(entity);
        Consumer<String> consumer = entity::setAuthorizationCodeValue;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer2 = entity::setAuthorizationCodeIssuedAt;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer3 = entity::setAuthorizationCodeExpiresAt;
        Objects.requireNonNull(entity);
        setTokenValues(token, consumer, consumer2, consumer3, entity::setAuthorizationCodeMetadata);
        OAuth2Authorization.Token<?> token2 = authorization.getToken(OAuth2AccessToken.class);
        Objects.requireNonNull(entity);
        Consumer<String> consumer4 = entity::setAccessTokenValue;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer5 = entity::setAccessTokenIssuedAt;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer6 = entity::setAccessTokenExpiresAt;
        Objects.requireNonNull(entity);
        setTokenValues(token2, consumer4, consumer5, consumer6, entity::setAccessTokenMetadata);
        if (token2 != null) {
            if (token2.getToken().getScopes() != null) {
                entity.setAccessTokenScopes(StringUtils.collectionToCommaDelimitedString(token2.getToken().getScopes()));
            }
            if (token2.getToken().getTokenType() != null) {
                entity.setAccessTokenType(token2.getToken().getTokenType().getValue());
            }
        }
        OAuth2Authorization.Token<?> token3 = authorization.getToken(OAuth2RefreshToken.class);
        Objects.requireNonNull(entity);
        Consumer<String> consumer7 = entity::setRefreshTokenValue;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer8 = entity::setRefreshTokenIssuedAt;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer9 = entity::setRefreshTokenExpiresAt;
        Objects.requireNonNull(entity);
        setTokenValues(token3, consumer7, consumer8, consumer9, entity::setRefreshTokenMetadata);
        OAuth2Authorization.Token<?> token4 = authorization.getToken(OidcIdToken.class);
        Objects.requireNonNull(entity);
        Consumer<String> consumer10 = entity::setOidcIdTokenValue;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer11 = entity::setOidcIdTokenIssuedAt;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer12 = entity::setOidcIdTokenExpiresAt;
        Objects.requireNonNull(entity);
        setTokenValues(token4, consumer10, consumer11, consumer12, entity::setOidcIdTokenMetadata);
        if (token4 != null) {
            entity.setOidcIdTokenClaims(writeMap(token4.getClaims()));
        }
        OAuth2Authorization.Token<?> token5 = authorization.getToken(OAuth2UserCode.class);
        Objects.requireNonNull(entity);
        Consumer<String> consumer13 = entity::setUserCodeValue;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer14 = entity::setUserCodeIssuedAt;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer15 = entity::setUserCodeExpiresAt;
        Objects.requireNonNull(entity);
        setTokenValues(token5, consumer13, consumer14, consumer15, entity::setUserCodeMetadata);
        OAuth2Authorization.Token<?> token6 = authorization.getToken(OAuth2DeviceCode.class);
        Objects.requireNonNull(entity);
        Consumer<String> consumer16 = entity::setDeviceCodeValue;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer17 = entity::setDeviceCodeIssuedAt;
        Objects.requireNonNull(entity);
        Consumer<LocalDateTime> consumer18 = entity::setDeviceCodeExpiresAt;
        Objects.requireNonNull(entity);
        setTokenValues(token6, consumer16, consumer17, consumer18, entity::setDeviceCodeMetadata);
        return entity;
    }

    private void setTokenValues(OAuth2Authorization.Token<?> token, Consumer<String> tokenValueConsumer, Consumer<LocalDateTime> issuedAtConsumer, Consumer<LocalDateTime> expiresAtConsumer, Consumer<String> metadataConsumer) {
        if (token != null) {
            OAuth2Token oAuth2Token = token.getToken();
            tokenValueConsumer.accept(oAuth2Token.getTokenValue());
            issuedAtConsumer.accept(DateUtil.toLocalDateTime(oAuth2Token.getIssuedAt()));
            expiresAtConsumer.accept(DateUtil.toLocalDateTime(oAuth2Token.getExpiresAt()));
            metadataConsumer.accept(writeMap(token.getMetadata()));
        }
    }
}
