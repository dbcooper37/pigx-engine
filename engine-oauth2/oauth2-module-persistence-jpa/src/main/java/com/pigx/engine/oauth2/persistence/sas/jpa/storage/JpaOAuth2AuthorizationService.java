package com.pigx.engine.oauth2.persistence.sas.jpa.storage;

import com.pigx.engine.oauth2.persistence.sas.jpa.converter.HerodotusToOAuth2AuthorizationConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.converter.OAuth2ToHerodotusAuthorizationConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorization;
import com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor;
import com.pigx.engine.oauth2.persistence.sas.jpa.service.HerodotusAuthorizationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/storage/JpaOAuth2AuthorizationService.class */
public class JpaOAuth2AuthorizationService implements OAuth2AuthorizationService {
    private static final Logger log = LoggerFactory.getLogger(JpaOAuth2AuthorizationService.class);
    private final HerodotusAuthorizationService herodotusAuthorizationService;
    private final Converter<HerodotusAuthorization, OAuth2Authorization> herodotusToOAuth2Converter;
    private final Converter<OAuth2Authorization, HerodotusAuthorization> oauth2ToHerodotusConverter;

    public JpaOAuth2AuthorizationService(HerodotusAuthorizationService herodotusAuthorizationService, RegisteredClientRepository registeredClientRepository) {
        this.herodotusAuthorizationService = herodotusAuthorizationService;
        OAuth2JacksonProcessor jacksonProcessor = new OAuth2JacksonProcessor();
        this.herodotusToOAuth2Converter = new HerodotusToOAuth2AuthorizationConverter(jacksonProcessor, registeredClientRepository);
        this.oauth2ToHerodotusConverter = new OAuth2ToHerodotusAuthorizationConverter(jacksonProcessor);
    }

    public void save(OAuth2Authorization authorization) {
        this.herodotusAuthorizationService.saveAndFlush(toEntity(authorization));
    }

    @Transactional
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        this.herodotusAuthorizationService.deleteById(authorization.getId());
        log.debug("[Herodotus] |- Jpa OAuth2 Authorization Service remove entity.");
        this.herodotusAuthorizationService.clearHistoryToken();
        log.debug("[Herodotus] |- Jpa OAuth2 Authorization Service clear history token.");
    }

    public OAuth2Authorization findById(String id) {
        return (OAuth2Authorization) this.herodotusAuthorizationService.findById(id).map(this::toObject).orElse(null);
    }

    public int findAuthorizationCount(String registeredClientId, String principalName) {
        int count = this.herodotusAuthorizationService.findAuthorizationCount(registeredClientId, principalName);
        log.debug("[Herodotus] |- Jpa OAuth2 Authorization Service findAuthorizationCount.");
        return count;
    }

    public List<OAuth2Authorization> findAvailableAuthorizations(String registeredClientId, String principalName) {
        List<HerodotusAuthorization> authorizations = this.herodotusAuthorizationService.findAvailableAuthorizations(registeredClientId, principalName);
        if (CollectionUtils.isNotEmpty(authorizations)) {
            return (List) authorizations.stream().map(this::toObject).collect(Collectors.toList());
        }
        return new ArrayList();
    }

    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Optional<HerodotusAuthorization> optionalEmpty;
        Optional<HerodotusAuthorization> result;
        Assert.hasText(token, "token cannot be empty");
        if (ObjectUtils.isEmpty(tokenType)) {
            result = this.herodotusAuthorizationService.findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(token);
        } else {
            switch (tokenType.getValue()) {
                case "state":
                    optionalEmpty = this.herodotusAuthorizationService.findByState(token);
                    break;
                case "code":
                    optionalEmpty = this.herodotusAuthorizationService.findByAuthorizationCode(token);
                    break;
                case "access_token":
                    optionalEmpty = this.herodotusAuthorizationService.findByAccessToken(token);
                    break;
                case "refresh_token":
                    optionalEmpty = this.herodotusAuthorizationService.findByRefreshToken(token);
                    break;
                case "id_token":
                    optionalEmpty = this.herodotusAuthorizationService.findByOidcIdTokenValue(token);
                    break;
                case "user_code":
                    optionalEmpty = this.herodotusAuthorizationService.findByUserCodeValue(token);
                    break;
                case "device_code":
                    optionalEmpty = this.herodotusAuthorizationService.findByDeviceCodeValue(token);
                    break;
                default:
                    optionalEmpty = Optional.empty();
                    break;
            }
            result = optionalEmpty;
        }
        return (OAuth2Authorization) result.map(this::toObject).orElse(null);
    }

    private OAuth2Authorization toObject(HerodotusAuthorization entity) {
        return (OAuth2Authorization) this.herodotusToOAuth2Converter.convert(entity);
    }

    private HerodotusAuthorization toEntity(OAuth2Authorization authorization) {
        return (HerodotusAuthorization) this.oauth2ToHerodotusConverter.convert(authorization);
    }
}
