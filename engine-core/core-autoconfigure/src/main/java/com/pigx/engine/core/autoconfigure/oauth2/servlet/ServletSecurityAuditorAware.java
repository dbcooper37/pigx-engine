package com.pigx.engine.autoconfigure.oauth2.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.definition.AbstractAuditorAware;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/servlet/ServletSecurityAuditorAware.class */
public class ServletSecurityAuditorAware extends AbstractAuditorAware implements AuditorAware<String> {
    public Optional<String> getCurrentAuditor() {
        SecurityContext context = SecurityContextHolder.getContext();
        Optional optionalFilter = Optional.ofNullable(context).map((v0) -> {
            return v0.getAuthentication();
        }).filter((v0) -> {
            return v0.isAuthenticated();
        }).filter(authentication -> {
            return authentication instanceof BearerTokenAuthentication;
        });
        Class<BearerTokenAuthentication> cls = BearerTokenAuthentication.class;
        Objects.requireNonNull(BearerTokenAuthentication.class);
        Optional optionalFilter2 = optionalFilter.map((v1) -> {
            return r1.cast(v1);
        }).map((v0) -> {
            return v0.getPrincipal();
        }).filter(object -> {
            return object instanceof OAuth2IntrospectionAuthenticatedPrincipal;
        });
        Class<OAuth2IntrospectionAuthenticatedPrincipal> cls2 = OAuth2IntrospectionAuthenticatedPrincipal.class;
        Objects.requireNonNull(OAuth2IntrospectionAuthenticatedPrincipal.class);
        return optionalFilter2.map(cls2::cast).map(x$0 -> {
            return this.getName(x$0);
        }).or(Optional::empty);
    }
}
