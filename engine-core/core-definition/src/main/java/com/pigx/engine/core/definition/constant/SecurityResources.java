package com.pigx.engine.core.definition.constant;

import com.google.common.collect.Lists;
import java.util.List;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/constant/SecurityResources.class */
public interface SecurityResources {
    public static final String MATCHER_WEBJARS = "/webjars/**";
    public static final String MATCHER_STATIC = "/static/**";
    public static final List<String> DEFAULT_IGNORED_STATIC_RESOURCES = Lists.newArrayList(new String[]{MATCHER_WEBJARS, MATCHER_STATIC, "/error/**", "/herodotus/**", "/swagger-ui.html", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs", "/v3/api-docs/**", "/openapi.json", "/favicon.ico"});
    public static final List<String> DEFAULT_PERMIT_ALL_RESOURCES = Lists.newArrayList(new String[]{"/open/**", "/stomp/ws", "/oauth2/sign-out", "/login*"});
    public static final List<String> DEFAULT_HAS_AUTHENTICATED_RESOURCES = Lists.newArrayList(new String[]{SystemConstants.OAUTH2_DEVICE_VERIFICATION_SUCCESS_URI, SystemConstants.OAUTH2_DEVICE_VERIFICATION_FAILURE_URI, SystemConstants.OAUTH2_AUTHORIZATION_CONSENT_URI, "/engine-rest/**"});
}
