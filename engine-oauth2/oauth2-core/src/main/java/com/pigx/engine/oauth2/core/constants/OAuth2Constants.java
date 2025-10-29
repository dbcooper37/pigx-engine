package com.pigx.engine.oauth2.core.constants;

import com.pigx.engine.core.definition.constant.BaseConstants;


public interface OAuth2Constants extends BaseConstants {

    String PROPERTY_SIGN_IN_FAILURE_LIMITED = PROPERTY_OAUTH2_AUTHENTICATION + ".sign-in-failure-limited";

    String ITEM_SIGN_IN_FAILURE_LIMITED_AUTO_UNLOCK = PROPERTY_SIGN_IN_FAILURE_LIMITED + ".auto-unlock";

    String REGION_OAUTH2_AUTHORIZATION = AREA_PREFIX + "oauth2:authorization";
    String REGION_OAUTH2_AUTHORIZATION_CONSENT = AREA_PREFIX + "oauth2:authorization:consent";
    String REGION_OAUTH2_REGISTERED_CLIENT = AREA_PREFIX + "oauth2:registered:client";
    String REGION_OAUTH2_APPLICATION = AREA_PREFIX + "oauth2:application";
    String REGION_OAUTH2_COMPLIANCE = AREA_PREFIX + "oauth2:compliance";
    String REGION_OAUTH2_PERMISSION = AREA_PREFIX + "oauth2:permission";
    String REGION_OAUTH2_SCOPE = AREA_PREFIX + "oauth2:scope";
    String REGION_OAUTH2_APPLICATION_SCOPE = AREA_PREFIX + "oauth2:application:scope";
    String REGION_OAUTH2_PRODUCT = AREA_PREFIX + "oauth2:product";
    String REGION_OAUTH2_DEVICE = AREA_PREFIX + "oauth2:device";

    String CACHE_NAME_TOKEN_SIGN_IN_FAILURE_LIMITED = CACHE_TOKEN_BASE_PREFIX + "sign_in:failure_limited:";
    String CACHE_NAME_TOKEN_LOCKED_USER_DETAIL = CACHE_TOKEN_BASE_PREFIX + "locked:user_details:";

    String CACHE_SECURITY_PREFIX = CACHE_PREFIX + "security:";
    String CACHE_SECURITY_METADATA_PREFIX = CACHE_SECURITY_PREFIX + "metadata:";

    String CACHE_NAME_SECURITY_METADATA_INDEXABLE = CACHE_SECURITY_METADATA_PREFIX + "indexable:";
    String CACHE_NAME_SECURITY_METADATA_COMPATIBLE = CACHE_SECURITY_METADATA_PREFIX + "compatible:";
}
