package com.pigx.engine.assistant.access.stamp;

import cn.hutool.v7.core.data.id.IdUtil;
import com.pigx.engine.assistant.access.constant.AccessConstants;
import com.pigx.engine.assistant.access.properties.JustAuthProperties;
import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import me.zhyd.oauth.cache.AuthStateCache;

import java.util.concurrent.TimeUnit;


public class JustAuthStateStampManager extends AbstractStampManager<String, String> implements AuthStateCache {

    public JustAuthStateStampManager(JustAuthProperties justAuthProperties) {
        super(AccessConstants.CACHE_NAME_TOKEN_JUSTAUTH, justAuthProperties.getTimeout());
    }

    @Override
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }

    @Override
    public void cache(String key, String value) {
        this.put(key, value);
    }

    @Override
    public void cache(String key, String value, long expire) {
        this.put(key, value, expire, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean containsKey(String key) {
        return this.containKey(key);
    }
}
