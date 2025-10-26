package com.pigx.engine.autoconfigure.oauth2.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.definition.UserPrincipalConverter;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import com.pigx.engine.core.identity.oauth2.BearerTokenResolver;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/servlet/HerodotusServletOpaqueTokenResolver.class */
public class HerodotusServletOpaqueTokenResolver implements BearerTokenResolver {
    private static final Logger log = LoggerFactory.getLogger(HerodotusServletOpaqueTokenResolver.class);
    private final OpaqueTokenIntrospector opaqueTokenIntrospector;

    public HerodotusServletOpaqueTokenResolver(OpaqueTokenIntrospector opaqueTokenIntrospector) {
        this.opaqueTokenIntrospector = opaqueTokenIntrospector;
    }

    @Override // com.pigx.engine.core.identity.oauth2.BearerTokenResolver
    public UserPrincipal resolve(String token) {
        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("token can not be null");
        }
        OAuth2AuthenticatedPrincipal principal = getOpaque(token);
        if (ObjectUtils.isNotEmpty(principal)) {
            UserPrincipal userPrincipal = UserPrincipalConverter.toUserPrincipal(principal);
            log.debug("[Herodotus] |- Resolve OPAQUE token to principal details [{}]", userPrincipal);
            return userPrincipal;
        }
        return null;
    }

    private OAuth2AuthenticatedPrincipal getOpaque(String token) {
        try {
            return this.opaqueTokenIntrospector.introspect(token);
        } catch (OAuth2IntrospectionException failed) {
            log.warn("[Herodotus] |- Failed to introspect Opaque, catch exception", failed);
            return null;
        } catch (BadOpaqueTokenException e) {
            log.warn("Failed to introspect since the Opaque was invalid");
            return null;
        }
    }
}
