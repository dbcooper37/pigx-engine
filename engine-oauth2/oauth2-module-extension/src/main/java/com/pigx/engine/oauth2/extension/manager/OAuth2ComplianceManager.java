package com.pigx.engine.oauth2.extension.manager;

import cn.hutool.v7.crypto.SecureUtil;
import com.pigx.engine.cache.core.exception.MaximumLimitExceededException;
import com.pigx.engine.oauth2.extension.converter.RequestToUserLoggingConverter;
import com.pigx.engine.oauth2.extension.entity.OAuth2UserLogging;
import com.pigx.engine.oauth2.extension.service.OAuth2UserLoggingService;
import com.pigx.engine.oauth2.extension.stamp.SignInFailureLimitedStampManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Component
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

    /**
     * 清除登录失败标记
     *
     * @param principal 用户
     */
    private void cleanSignInFailureTimes(String principal) {
        String key = SecureUtil.md5(principal);
        boolean hasKey = stampManager.containKey(key);
        if (hasKey) {
            log.debug("[PIGXD] |- Clean sign in failure stamp for user [{}].", principal);
            stampManager.delete(key);
        }
    }

    /**
     * 登录失败计数
     *
     * @param principal 用户
     */
    public void countingSignInFailureTimes(String principal) {
        log.debug("[PIGXD] |- Parse the user name in failure event is [{}].", principal);

        int maxTimes = stampManager.getAuthenticationProperties().getSignInFailureLimited().getMaxTimes();
        Duration expire = stampManager.getAuthenticationProperties().getSignInFailureLimited().getExpire();
        try {
            int times = stampManager.counting(principal, maxTimes, expire, true, "AuthenticationFailureListener");
            log.debug("[PIGXD] |- Sign in user input password error [{}] items", times);
        } catch (MaximumLimitExceededException e) {
            log.warn("[PIGXD] |- User [{}] password error [{}] items, LOCK ACCOUNT!", principal, maxTimes);
            accountStatusManager.lock(principal);
        }
    }

    /**
     * 重新设置用户账号为可用状态。即解除账号锁定状态
     *
     * @param userId 用户 ID
     */
    public void enable(String userId) {
        accountStatusManager.enable(userId);
    }

    /**
     * 记录用户登录信息
     *
     * @param token   Token 信息
     * @param request 请求
     */
    public void signIn(OAuth2AccessTokenAuthenticationToken token, HttpServletRequest request) {
        Converter<HttpServletRequest, OAuth2UserLogging> toUserLogging = new RequestToUserLoggingConverter(token);
        OAuth2UserLogging userLogging = toUserLogging.convert(request);

        if (ObjectUtils.isNotEmpty(userLogging)) {
            OAuth2UserLogging result = userLoggingService.save(userLogging);
            if (ObjectUtils.isNotEmpty(result) && StringUtils.isNotBlank(result.getPrincipalName())) {
                // 清除登录失败标记
                cleanSignInFailureTimes(result.getPrincipalName());
            }
        }
    }

    /**
     * 记录用户登出信息
     *
     * @param authorization 认证信息
     * @param request       请求
     */
    public void signOut(OAuth2Authorization authorization, HttpServletRequest request) {
        Converter<HttpServletRequest, OAuth2UserLogging> toUserLogging = new RequestToUserLoggingConverter(authorization);
        OAuth2UserLogging userLogging = toUserLogging.convert(request);

        if (ObjectUtils.isNotEmpty(userLogging)) {
            userLoggingService.save(userLogging);
            accountStatusManager.releaseFromCache(authorization.getPrincipalName());
        }
    }
}
