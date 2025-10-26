package com.pigx.engine.assistant.access.stamp;

import com.pigx.engine.assistant.access.constant.AccessConstants;
import com.pigx.engine.assistant.access.properties.JustAuthProperties;
import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import cn.hutool.v7.core.data.id.IdUtil;
import java.util.concurrent.TimeUnit;
import me.zhyd.oauth.cache.AuthStateCache;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/stamp/JustAuthStateStampManager.class */
public class JustAuthStateStampManager extends AbstractStampManager<String, String> implements AuthStateCache {
    public /* bridge */ /* synthetic */ String get(String key) {
        return (String) super.get((JustAuthStateStampManager) key);
    }

    public JustAuthStateStampManager(JustAuthProperties justAuthProperties) {
        super(AccessConstants.CACHE_NAME_TOKEN_JUSTAUTH, justAuthProperties.getTimeout());
    }

    @Override // com.pigx.engine.cache.jetcache.stamp.StampManager
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }

    public void cache(String key, String value) {
        put(key, value);
    }

    public void cache(String key, String value, long expire) {
        put(key, value, expire, TimeUnit.MILLISECONDS);
    }

    public boolean containsKey(String key) {
        return containKey(key);
    }
}
