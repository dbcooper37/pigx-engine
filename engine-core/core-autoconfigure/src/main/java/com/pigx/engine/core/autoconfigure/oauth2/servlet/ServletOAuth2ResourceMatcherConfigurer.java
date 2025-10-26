package com.pigx.engine.autoconfigure.oauth2.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.OAuth2AuthorizationProperties;
import com.pigx.engine.core.autoconfigure.oauth2.definition.AbstractSecurityMatcherConfigurer;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/servlet/ServletOAuth2ResourceMatcherConfigurer.class */
public class ServletOAuth2ResourceMatcherConfigurer extends AbstractSecurityMatcherConfigurer {
    private final ResourceUrlProvider resourceUrlProvider;
    private final RequestMatcher[] staticRequestMatchers;
    private final RequestMatcher[] permitAllRequestMatchers;
    private final RequestMatcher[] hasAuthenticatedRequestMatchers;

    public ServletOAuth2ResourceMatcherConfigurer(OAuth2AuthorizationProperties authorizationProperties, ResourceUrlProvider resourceUrlProvider) {
        super(authorizationProperties);
        this.resourceUrlProvider = resourceUrlProvider;
        this.staticRequestMatchers = WebPathUtils.toRequestMatchers(getStaticResources());
        this.permitAllRequestMatchers = WebPathUtils.toRequestMatchers(getPermitAllResources());
        this.hasAuthenticatedRequestMatchers = WebPathUtils.toRequestMatchers(getHasAuthenticatedResources());
    }

    public RequestMatcher[] getStaticRequestMatchers() {
        return this.staticRequestMatchers;
    }

    public RequestMatcher[] getPermitAllRequestMatchers() {
        return this.permitAllRequestMatchers;
    }

    public RequestMatcher[] getHasAuthenticatedRequestMatchers() {
        return this.hasAuthenticatedRequestMatchers;
    }

    public boolean isStaticRequest(String uri) {
        String staticUri = this.resourceUrlProvider.getForLookupPath(uri);
        return StringUtils.isNotBlank(staticUri);
    }

    public boolean isPermitAllRequest(HttpServletRequest request) {
        return WebPathUtils.isRequestMatched(getPermitAllRequestMatchers(), request);
    }

    public boolean isHasAuthenticatedRequest(HttpServletRequest request) {
        return WebPathUtils.isRequestMatched(getHasAuthenticatedRequestMatchers(), request);
    }
}
