package com.pigx.engine.oauth2.extension.stamp;

import cn.hutool.v7.core.data.id.IdUtil;
import com.pigx.engine.cache.jetcache.stamp.AbstractStampManager;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import org.springframework.stereotype.Component;


@Component
public class LockedAccountStampManager extends AbstractStampManager<String, String> {

    public LockedAccountStampManager(OAuth2AuthenticationProperties authenticationProperties) {
        super(OAuth2Constants.CACHE_NAME_TOKEN_LOCKED_USER_DETAIL, authenticationProperties.getSignInFailureLimited().getExpire());
    }

    @Override
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }
}
