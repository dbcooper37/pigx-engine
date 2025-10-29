package com.pigx.engine.web.service.stamp;

import cn.hutool.v7.core.data.id.IdUtil;
import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import com.pigx.engine.web.core.constant.WebConstants;
import com.pigx.engine.web.service.properties.SecureProperties;


public class IdempotentStampManager extends AbstractStampManager<String, String> {

    private final SecureProperties secureProperties;

    public IdempotentStampManager(SecureProperties secureProperties) {
        super(WebConstants.CACHE_NAME_TOKEN_IDEMPOTENT, secureProperties.getIdempotent().getExpire());
        this.secureProperties = secureProperties;
    }

    public SecureProperties getSecureProperties() {
        return secureProperties;
    }

    @Override
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }
}
