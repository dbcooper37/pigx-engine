package com.pigx.engine.core.autoconfigure.oauth2.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.definition.AbstractAuditorAware;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;

import java.util.Optional;


public class ServletSecurityAuditorAware extends AbstractAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        SecurityContext context = SecurityContextHolder.getContext();

        return Optional.ofNullable(context)
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .filter(authentication -> authentication instanceof BearerTokenAuthentication)
                .map(BearerTokenAuthentication.class::cast)
                .map(AbstractOAuth2TokenAuthenticationToken::getPrincipal)
                .filter(object -> object instanceof OAuth2IntrospectionAuthenticatedPrincipal)
                .map(OAuth2IntrospectionAuthenticatedPrincipal.class::cast)
                .map(this::getName)
                .or(Optional::empty);
    }
}
