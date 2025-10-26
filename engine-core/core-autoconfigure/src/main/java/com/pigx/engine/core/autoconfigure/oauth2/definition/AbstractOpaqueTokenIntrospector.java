package com.pigx.engine.autoconfigure.oauth2.definition;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/definition/AbstractOpaqueTokenIntrospector.class */
public abstract class AbstractOpaqueTokenIntrospector {
    protected OAuth2AuthenticatedPrincipal convertClaimsSet(Map<String, Object> claims) {
        claims.computeIfPresent("aud", (k, v) -> {
            if (v instanceof String) {
                return Collections.singletonList(v);
            }
            return v;
        });
        claims.computeIfPresent("client_id", (k2, v2) -> {
            return v2.toString();
        });
        claims.computeIfPresent("exp", (k3, v3) -> {
            return Instant.ofEpochSecond(((Number) v3).longValue());
        });
        claims.computeIfPresent("iat", (k4, v4) -> {
            return Instant.ofEpochSecond(((Number) v4).longValue());
        });
        claims.computeIfPresent("iss", (k5, v5) -> {
            return v5.toString();
        });
        claims.computeIfPresent("nbf", (k6, v6) -> {
            return Instant.ofEpochSecond(((Number) v6).longValue());
        });
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        claims.computeIfPresent("scope", (k7, v7) -> {
            return v7.toString();
        });
        claims.computeIfPresent(SystemConstants.AUTHORITIES, (k8, v8) -> {
            if (v8 instanceof ArrayList) {
                List<String> values = (List) v8;
                values.forEach(value -> {
                    authorities.add(new HerodotusGrantedAuthority(value));
                });
            }
            return v8;
        });
        return new OAuth2IntrospectionAuthenticatedPrincipal(claims, authorities);
    }
}
