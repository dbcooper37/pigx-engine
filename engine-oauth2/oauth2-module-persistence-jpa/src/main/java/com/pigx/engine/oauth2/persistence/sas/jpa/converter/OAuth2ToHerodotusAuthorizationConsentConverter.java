package com.pigx.engine.oauth2.persistence.sas.jpa.converter;

import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorizationConsent;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.util.StringUtils;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/converter/OAuth2ToHerodotusAuthorizationConsentConverter.class */
public class OAuth2ToHerodotusAuthorizationConsentConverter implements Converter<OAuth2AuthorizationConsent, HerodotusAuthorizationConsent> {
    public HerodotusAuthorizationConsent convert(OAuth2AuthorizationConsent authorizationConsent) {
        HerodotusAuthorizationConsent entity = new HerodotusAuthorizationConsent();
        entity.setRegisteredClientId(authorizationConsent.getRegisteredClientId());
        entity.setPrincipalName(authorizationConsent.getPrincipalName());
        Set<String> authorities = new HashSet<>();
        for (GrantedAuthority authority : authorizationConsent.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
        entity.setAuthorities(StringUtils.collectionToCommaDelimitedString(authorities));
        return entity;
    }
}
