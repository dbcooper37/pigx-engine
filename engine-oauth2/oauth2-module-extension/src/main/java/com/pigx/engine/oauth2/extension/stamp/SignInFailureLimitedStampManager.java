package com.pigx.engine.oauth2.extension.stamp;

import cn.hutool.v7.crypto.SecureUtil;
import com.pigx.engine.cache.jetcache.stamp.AbstractCountStampManager;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import com.pigx.engine.oauth2.core.properties.OAuth2AuthenticationProperties;
import com.pigx.engine.oauth2.extension.dto.SignInErrorStatus;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;


@Component
public class SignInFailureLimitedStampManager extends AbstractCountStampManager {

    private final OAuth2AuthenticationProperties authenticationProperties;

    public SignInFailureLimitedStampManager(OAuth2AuthenticationProperties authenticationProperties) {
        super(OAuth2Constants.CACHE_NAME_TOKEN_SIGN_IN_FAILURE_LIMITED, authenticationProperties.getSignInFailureLimited().getExpire());
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public Long nextStamp(String key) {
        return 1L;
    }

    public OAuth2AuthenticationProperties getAuthenticationProperties() {
        return authenticationProperties;
    }

    public SignInErrorStatus errorStatus(String username) {
        int maxTimes = authenticationProperties.getSignInFailureLimited().getMaxTimes();
        Long storedTimes = get(SecureUtil.md5(username));

        int errorTimes = 0;
        if (ObjectUtils.isNotEmpty(storedTimes)) {
            errorTimes = storedTimes.intValue();
        }

        int remainTimes = maxTimes;
        if (errorTimes != 0) {
            remainTimes = maxTimes - errorTimes;
        }

        boolean isLocked = false;
        if (errorTimes == maxTimes) {
            isLocked = true;
        }

        SignInErrorStatus status = new SignInErrorStatus();
        status.setErrorTimes(errorTimes);
        status.setRemainTimes(remainTimes);
        status.setLocked(isLocked);

        return status;
    }
}
