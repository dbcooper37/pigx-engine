package com.pigx.engine.web.core.constant;

import com.pigx.engine.core.definition.constant.BaseConstants;


public interface WebConstants extends BaseConstants {

    String PROPERTY_PREFIX_SWAGGER = PROPERTY_PREFIX_PLATFORM + ".swagger";
    String PROPERTY_SERVICE_REST_SCAN = PROPERTY_PREFIX_SERVICE + ".scan";

    String ITEM_SCAN_ENABLED = PROPERTY_SERVICE_REST_SCAN + PROPERTY_ENABLED;
    String ITEM_PROTECT_CRYPTO_STRATEGY = PROPERTY_PREFIX_CRYPTO + ".crypto-strategy";

    String CACHE_NAME_TOKEN_IDEMPOTENT = CACHE_TOKEN_BASE_PREFIX + "idempotent:";
    String CACHE_NAME_TOKEN_ACCESS_LIMITED = CACHE_TOKEN_BASE_PREFIX + "access_limited:";
    String CACHE_NAME_TOKEN_SECURE_KEY = CACHE_TOKEN_BASE_PREFIX + "secure_key:";
}
