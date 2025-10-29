package com.pigx.engine.core.autoconfigure.oauth2.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.OAuth2AuthorizationProperties;
import com.pigx.engine.core.autoconfigure.oauth2.definition.AbstractSecurityMatcherConfigurer;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.resource.ResourceUrlProvider;


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
        return staticRequestMatchers;
    }

    public RequestMatcher[] getPermitAllRequestMatchers() {
        return permitAllRequestMatchers;
    }

    public RequestMatcher[] getHasAuthenticatedRequestMatchers() {
        return hasAuthenticatedRequestMatchers;
    }

    /**
     * 判断是否为静态资源
     *
     * @param uri 请求 URL
     * @return 是否为静态资源
     */
    public boolean isStaticRequest(String uri) {
        String staticUri = resourceUrlProvider.getForLookupPath(uri);
        return StringUtils.isNotBlank(staticUri);
    }

    public boolean isPermitAllRequest(HttpServletRequest request) {
        return WebPathUtils.isRequestMatched(getPermitAllRequestMatchers(), request);
    }

    public boolean isHasAuthenticatedRequest(HttpServletRequest request) {
        return WebPathUtils.isRequestMatched(getHasAuthenticatedRequestMatchers(), request);
    }
}
