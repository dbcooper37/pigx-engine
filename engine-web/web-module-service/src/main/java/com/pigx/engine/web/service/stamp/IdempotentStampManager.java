package com.pigx.engine.web.service.stamp;

import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import com.pigx.engine.web.core.constant.WebConstants;
import com.pigx.engine.web.service.properties.SecureProperties;
import cn.hutool.v7.core.data.id.IdUtil;

/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/stamp/IdempotentStampManager.class */
public class IdempotentStampManager extends AbstractStampManager<String, String> {
    private final SecureProperties secureProperties;

    public IdempotentStampManager(SecureProperties secureProperties) {
        super(WebConstants.CACHE_NAME_TOKEN_IDEMPOTENT, secureProperties.getIdempotent().getExpire());
        this.secureProperties = secureProperties;
    }

    public SecureProperties getSecureProperties() {
        return this.secureProperties;
    }

    @Override // com.pigx.engine.cache.jetcache.stamp.StampManager
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }
}
