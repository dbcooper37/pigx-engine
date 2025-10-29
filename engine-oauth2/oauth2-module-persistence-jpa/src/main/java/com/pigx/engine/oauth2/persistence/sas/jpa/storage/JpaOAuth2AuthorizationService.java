package com.pigx.engine.oauth2.persistence.sas.jpa.storage;

import com.pigx.engine.oauth2.persistence.sas.jpa.converter.HerodotusToOAuth2AuthorizationConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.converter.OAuth2ToHerodotusAuthorizationConverter;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorization;
import com.pigx.engine.oauth2.persistence.sas.jpa.jackson2.OAuth2JacksonProcessor;
import com.pigx.engine.oauth2.persistence.sas.jpa.service.HerodotusAuthorizationService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @Override
    public void save(OAuth2Authorization authorization) {
        this.herodotusAuthorizationService.saveAndFlush(toEntity(authorization));
    }

    @Transactional
    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        this.herodotusAuthorizationService.deleteById(authorization.getId());
        log.debug("[PIGXD] |- Jpa OAuth2 Authorization Service remove entity.");
        // TODO： 后期还是考虑改为异步任务的形式，先临时放在这里。
        this.herodotusAuthorizationService.clearHistoryToken();
        log.debug("[PIGXD] |- Jpa OAuth2 Authorization Service clear history token.");
    }

    @Override
    public OAuth2Authorization findById(String id) {
        Optional<HerodotusAuthorization> herodotusAuthorization = this.herodotusAuthorizationService.findById(id);
        return herodotusAuthorization.map(this::toObject).orElse(null);
    }

    public int findAuthorizationCount(String registeredClientId, String principalName) {
        int count = this.herodotusAuthorizationService.findAuthorizationCount(registeredClientId, principalName);
        log.debug("[PIGXD] |- Jpa OAuth2 Authorization Service findAuthorizationCount.");
        return count;
    }

    public List<OAuth2Authorization> findAvailableAuthorizations(String registeredClientId, String principalName) {
        List<HerodotusAuthorization> authorizations = this.herodotusAuthorizationService.findAvailableAuthorizations(registeredClientId, principalName);
        if (CollectionUtils.isNotEmpty(authorizations)) {
            return authorizations.stream().map(this::toObject).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");

        Optional<HerodotusAuthorization> result;

        if (ObjectUtils.isEmpty(tokenType)) {
            result = this.herodotusAuthorizationService.findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(token);
        } else {
            result = switch (tokenType.getValue()) {
                case OAuth2ParameterNames.STATE -> this.herodotusAuthorizationService.findByState(token);
                case OAuth2ParameterNames.CODE -> this.herodotusAuthorizationService.findByAuthorizationCode(token);
                case OAuth2ParameterNames.ACCESS_TOKEN -> this.herodotusAuthorizationService.findByAccessToken(token);
                case OAuth2ParameterNames.REFRESH_TOKEN -> this.herodotusAuthorizationService.findByRefreshToken(token);
                case OidcParameterNames.ID_TOKEN -> this.herodotusAuthorizationService.findByOidcIdTokenValue(token);
                case OAuth2ParameterNames.USER_CODE -> this.herodotusAuthorizationService.findByUserCodeValue(token);
                case OAuth2ParameterNames.DEVICE_CODE ->
                        this.herodotusAuthorizationService.findByDeviceCodeValue(token);
                default -> Optional.empty();
            };
        }

        return result.map(this::toObject).orElse(null);
    }

    private OAuth2Authorization toObject(HerodotusAuthorization entity) {
        return herodotusToOAuth2Converter.convert(entity);
    }

    private HerodotusAuthorization toEntity(OAuth2Authorization authorization) {
        return oauth2ToHerodotusConverter.convert(authorization);
    }
}
