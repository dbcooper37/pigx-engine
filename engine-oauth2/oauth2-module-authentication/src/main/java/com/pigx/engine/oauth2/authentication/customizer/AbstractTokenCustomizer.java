package com.pigx.engine.oauth2.authentication.customizer;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/customizer/AbstractTokenCustomizer.class */
public abstract class AbstractTokenCustomizer {
    protected void appendAll(Map<String, Object> attributes, Authentication authentication, Set<String> authorizedScopes) {
        appendAuthorities(attributes, authentication);
        appendCommons(attributes, authentication, authorizedScopes);
    }

    protected void appendAuthorities(Map<String, Object> attributes, Authentication authentication) {
        if (CollectionUtils.isNotEmpty(authentication.getAuthorities())) {
            Set<String> authorities = (Set) authentication.getAuthorities().stream().map((v0) -> {
                return v0.getAuthority();
            }).collect(Collectors.toSet());
            attributes.put(SystemConstants.AUTHORITIES, authorities);
        }
    }

    protected void appendCommons(Map<String, Object> attributes, Authentication authentication, Set<String> authorizedScopes) {
        if (CollectionUtils.isNotEmpty(authorizedScopes)) {
            attributes.put("scope", authorizedScopes);
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            putUserInfo(attributes, authentication.getPrincipal());
        }
        if (authentication instanceof OAuth2AccessTokenAuthenticationToken) {
            Object details = authentication.getDetails();
            putUserInfo(attributes, details);
        }
        attributes.put(SystemConstants.LICENSE, "Apache-2.0 Licensed | Copyright © 2020-2023 码 匠 君");
    }

    private void putUserInfo(Map<String, Object> attributes, Object object) {
        if (ObjectUtils.isNotEmpty(object) && (object instanceof HerodotusUser)) {
            HerodotusUser principal = (HerodotusUser) object;
            attributes.put(SystemConstants.SCOPE_OPENID, principal.getUserId());
            attributes.put(SystemConstants.ROLES, principal.getRoles());
            attributes.put(SystemConstants.AVATAR, principal.getAvatar());
            attributes.put(SystemConstants.EMPLOYEE_ID, principal.getEmployeeId());
        }
    }
}
