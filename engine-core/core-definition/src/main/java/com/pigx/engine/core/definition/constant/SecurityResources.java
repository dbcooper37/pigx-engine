package com.pigx.engine.core.definition.constant;

import com.google.common.collect.Lists;

import java.util.List;


public interface SecurityResources {

    String MATCHER_WEBJARS = "/webjars/**";
    String MATCHER_STATIC = "/static/**";

    List<String> DEFAULT_IGNORED_STATIC_RESOURCES = Lists.newArrayList(MATCHER_WEBJARS, MATCHER_STATIC,
            "/error/**",
            "/herodotus/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/openapi.json",
            "/favicon.ico"
    );
    List<String> DEFAULT_PERMIT_ALL_RESOURCES = Lists.newArrayList(
            "/open/**",
            "/stomp/ws",
            "/oauth2/sign-out",
            "/login*"
    );

    List<String> DEFAULT_HAS_AUTHENTICATED_RESOURCES = Lists.newArrayList(
            SystemConstants.OAUTH2_DEVICE_VERIFICATION_SUCCESS_URI,
            SystemConstants.OAUTH2_DEVICE_VERIFICATION_FAILURE_URI,
            SystemConstants.OAUTH2_AUTHORIZATION_CONSENT_URI,
            "/engine-rest/**"
    );
}
