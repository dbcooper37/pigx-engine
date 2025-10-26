package com.pigx.engine.oauth2.extension.stamp;

import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import cn.hutool.v7.core.data.id.IdUtil;
import org.springframework.stereotype.Component;

@Component
/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/stamp/LockedAccountStampManager.class */
public class LockedAccountStampManager extends AbstractStampManager<String, String> {
    public LockedAccountStampManager(OAuth2AuthenticationProperties authenticationProperties) {
        super(OAuth2Constants.CACHE_NAME_TOKEN_LOCKED_USER_DETAIL, authenticationProperties.getSignInFailureLimited().getExpire());
    }

    @Override // com.pigx.engine.cache.jetcache.stamp.StampManager
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }
}
