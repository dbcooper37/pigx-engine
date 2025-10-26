package com.pigx.engine.oauth2.extension.manager;

import com.pigx.engine.cache.core.exception.MaximumLimitExceededException;
import com.pigx.engine.oauth2.extension.converter.RequestToUserLoggingConverter;
import com.pigx.engine.oauth2.extension.entity.OAuth2UserLogging;
import com.pigx.engine.oauth2.extension.service.OAuth2UserLoggingService;
import com.pigx.engine.oauth2.extension.stamp.SignInFailureLimitedStampManager;
import cn.hutool.v7.crypto.SecureUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/manager/OAuth2ComplianceManager.class */
public class OAuth2ComplianceManager {
    private static final Logger log = LoggerFactory.getLogger(OAuth2ComplianceManager.class);
    private final OAuth2UserLoggingService userLoggingService;
    private final OAuth2AccountStatusManager accountStatusManager;
    private final SignInFailureLimitedStampManager stampManager;

    public OAuth2ComplianceManager(OAuth2UserLoggingService userLoggingService, OAuth2AccountStatusManager accountStatusManager, SignInFailureLimitedStampManager stampManager) {
        this.userLoggingService = userLoggingService;
        this.accountStatusManager = accountStatusManager;
        this.stampManager = stampManager;
    }

    private void cleanSignInFailureTimes(String principal) {
        String key = SecureUtil.md5(principal);
        boolean hasKey = this.stampManager.containKey(key);
        if (hasKey) {
            log.debug("[Herodotus] |- Clean sign in failure stamp for user [{}].", principal);
            this.stampManager.delete(key);
        }
    }

    public void countingSignInFailureTimes(String principal) throws UsernameNotFoundException {
        log.debug("[Herodotus] |- Parse the user name in failure event is [{}].", principal);
        int maxTimes = this.stampManager.getAuthenticationProperties().getSignInFailureLimited().getMaxTimes().intValue();
        Duration expire = this.stampManager.getAuthenticationProperties().getSignInFailureLimited().getExpire();
        try {
            int times = this.stampManager.counting(principal, maxTimes, expire, true, "AuthenticationFailureListener");
            log.debug("[Herodotus] |- Sign in user input password error [{}] items", Integer.valueOf(times));
        } catch (MaximumLimitExceededException e) {
            log.warn("[Herodotus] |- User [{}] password error [{}] items, LOCK ACCOUNT!", principal, Integer.valueOf(maxTimes));
            this.accountStatusManager.lock(principal);
        }
    }

    public void enable(String userId) {
        this.accountStatusManager.enable(userId);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void signIn(OAuth2AccessTokenAuthenticationToken token, HttpServletRequest request) {
        Converter<HttpServletRequest, OAuth2UserLogging> toUserLogging = new RequestToUserLoggingConverter(token);
        OAuth2UserLogging userLogging = (OAuth2UserLogging) toUserLogging.convert(request);
        if (ObjectUtils.isNotEmpty(userLogging)) {
            OAuth2UserLogging result = (OAuth2UserLogging) this.userLoggingService.save(userLogging);
            if (ObjectUtils.isNotEmpty(result) && StringUtils.isNotBlank(result.getPrincipalName())) {
                cleanSignInFailureTimes(result.getPrincipalName());
            }
        }
    }

    public void signOut(OAuth2Authorization authorization, HttpServletRequest request) throws UsernameNotFoundException {
        Converter<HttpServletRequest, OAuth2UserLogging> toUserLogging = new RequestToUserLoggingConverter(authorization);
        OAuth2UserLogging userLogging = (OAuth2UserLogging) toUserLogging.convert(request);
        if (ObjectUtils.isNotEmpty(userLogging)) {
            this.userLoggingService.save(userLogging);
            this.accountStatusManager.releaseFromCache(authorization.getPrincipalName());
        }
    }
}
