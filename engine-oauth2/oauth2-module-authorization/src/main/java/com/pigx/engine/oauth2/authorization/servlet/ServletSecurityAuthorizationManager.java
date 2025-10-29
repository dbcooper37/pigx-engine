package com.pigx.engine.oauth2.authorization.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.domain.HerodotusRequest;
import com.pigx.engine.core.autoconfigure.oauth2.servlet.ServletOAuth2ResourceMatcherConfigurer;
import com.pigx.engine.core.identity.domain.HerodotusSecurityAttribute;
import com.pigx.engine.oauth2.authorization.processor.SecurityAttributeStorage;
import com.pigx.engine.web.core.servlet.utils.HeaderUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


public class ServletSecurityAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final Logger log = LoggerFactory.getLogger(ServletSecurityAuthorizationManager.class);

    private final SecurityAttributeStorage securityAttributeStorage;
    private final ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer;

    public ServletSecurityAuthorizationManager(SecurityAttributeStorage securityAttributeStorage, ServletOAuth2ResourceMatcherConfigurer servletOAuth2ResourceMatcherConfigurer) {
        this.securityAttributeStorage = securityAttributeStorage;
        this.servletOAuth2ResourceMatcherConfigurer = servletOAuth2ResourceMatcherConfigurer;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        final HttpServletRequest request = object.getRequest();

        String url = request.getRequestURI();
        String method = request.getMethod();

        if (servletOAuth2ResourceMatcherConfigurer.isStaticRequest(url)) {
            log.trace("[PIGXD] |- Is static resource : [{}], Passed!", url);
            return new AuthorizationDecision(true);
        }

        if (servletOAuth2ResourceMatcherConfigurer.isPermitAllRequest(request)) {
            log.trace("[PIGXD] |- Is white list resource : [{}], Passed!", url);
            return new AuthorizationDecision(true);
        }

        String feignInnerFlag = HeaderUtils.getHerodotusFromIn(request);
        if (StringUtils.isNotBlank(feignInnerFlag)) {
            log.trace("[PIGXD] |- Is feign inner invoke : [{}], Passed!", url);
            return new AuthorizationDecision(true);
        }

        if (servletOAuth2ResourceMatcherConfigurer.isHasAuthenticatedRequest(request)) {
            log.trace("[PIGXD] |- Is has authenticated resource : [{}]", url);
            return new AuthorizationDecision(authentication.get().isAuthenticated());
        }

        List<HerodotusSecurityAttribute> configAttributes = findConfigAttribute(url, method, request);
        if (CollectionUtils.isEmpty(configAttributes)) {
            log.warn("[PIGXD] |- NO PRIVILEGES : [{}].", url);

            if (!servletOAuth2ResourceMatcherConfigurer.isStrictMode()) {
                if (authentication.get().isAuthenticated()) {
                    log.debug("[PIGXD] |- Request is authenticated: [{}].", url);
                    return new AuthorizationDecision(true);
                }
            }

            return new AuthorizationDecision(false);
        }

        for (HerodotusSecurityAttribute configAttribute : configAttributes) {
            WebExpressionAuthorizationManager webExpressionAuthorizationManager = new WebExpressionAuthorizationManager(configAttribute.getAttribute());
            AuthorizationDecision decision = webExpressionAuthorizationManager.check(authentication, object);
            if (decision.isGranted()) {
                log.debug("[PIGXD] |- Request [{}] is authorized!", object.getRequest().getRequestURI());
                return decision;
            }
        }

        return new AuthorizationDecision(false);
    }

    private List<HerodotusSecurityAttribute> findConfigAttribute(String url, String method, HttpServletRequest request) {

        log.debug("[PIGXD] |- Current Request is : [{}] - [{}]", url, method);

        List<HerodotusSecurityAttribute> configAttributes = this.securityAttributeStorage.getConfigAttribute(url, method);
        if (CollectionUtils.isNotEmpty(configAttributes)) {
            log.debug("[PIGXD] |- Get configAttributes from local storage for : [{}] - [{}]", url, method);
            return configAttributes;
        } else {
            LinkedHashMap<HerodotusRequest, List<HerodotusSecurityAttribute>> compatible = this.securityAttributeStorage.getCompatible();
            if (MapUtils.isNotEmpty(compatible)) {
                // 支持含有**通配符的路径搜索
                for (Map.Entry<HerodotusRequest, List<HerodotusSecurityAttribute>> entry : compatible.entrySet()) {
                    RequestMatcher.MatchResult matchResult = matches(request, entry.getKey());
                    if (matchResult.isMatch()) {
                        log.debug("[PIGXD] |- Request match the wildcard [{}] - [{}]", entry.getKey(), entry.getValue());
                        return entry.getValue();
                    }
                }
            }
        }

        return null;
    }

    private RequestMatcher.MatchResult matches(HttpServletRequest httpServletRequest, HerodotusRequest request) {
        HttpMethod httpMethod = null;
        if (StringUtils.isNotBlank(request.getHttpMethod())) {
            httpMethod = HttpMethod.valueOf(request.getHttpMethod());
        }

        PathPatternRequestMatcher matcher = PathPatternRequestMatcher.withDefaults().matcher(httpMethod, request.getPattern());
        return matcher.matcher(httpServletRequest);
    }
}
