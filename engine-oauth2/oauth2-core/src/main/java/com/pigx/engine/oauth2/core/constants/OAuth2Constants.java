package com.pigx.engine.oauth2.core.constants;

import com.pigx.engine.core.definition.constant.BaseConstants;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/constants/OAuth2Constants.class */
public interface OAuth2Constants extends BaseConstants {
    public static final String PROPERTY_SIGN_IN_FAILURE_LIMITED = "herodotus.oauth2.authentication.sign-in-failure-limited";
    public static final String ITEM_SIGN_IN_FAILURE_LIMITED_AUTO_UNLOCK = "herodotus.oauth2.authentication.sign-in-failure-limited.auto-unlock";
    public static final String REGION_OAUTH2_AUTHORIZATION = "data:oauth2:authorization";
    public static final String REGION_OAUTH2_AUTHORIZATION_CONSENT = "data:oauth2:authorization:consent";
    public static final String REGION_OAUTH2_REGISTERED_CLIENT = "data:oauth2:registered:client";
    public static final String REGION_OAUTH2_APPLICATION = "data:oauth2:application";
    public static final String REGION_OAUTH2_COMPLIANCE = "data:oauth2:compliance";
    public static final String REGION_OAUTH2_PERMISSION = "data:oauth2:permission";
    public static final String REGION_OAUTH2_SCOPE = "data:oauth2:scope";
    public static final String REGION_OAUTH2_APPLICATION_SCOPE = "data:oauth2:application:scope";
    public static final String REGION_OAUTH2_PRODUCT = "data:oauth2:product";
    public static final String REGION_OAUTH2_DEVICE = "data:oauth2:device";
    public static final String CACHE_NAME_TOKEN_SIGN_IN_FAILURE_LIMITED = "cache:token:sign_in:failure_limited:";
    public static final String CACHE_NAME_TOKEN_LOCKED_USER_DETAIL = "cache:token:locked:user_details:";
    public static final String CACHE_SECURITY_PREFIX = "cache:security:";
    public static final String CACHE_SECURITY_METADATA_PREFIX = "cache:security:metadata:";
    public static final String CACHE_NAME_SECURITY_METADATA_INDEXABLE = "cache:security:metadata:indexable:";
    public static final String CACHE_NAME_SECURITY_METADATA_COMPATIBLE = "cache:security:metadata:compatible:";
}
