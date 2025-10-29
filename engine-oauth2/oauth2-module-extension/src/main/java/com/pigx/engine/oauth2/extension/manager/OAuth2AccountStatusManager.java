package com.pigx.engine.oauth2.extension.manager;

import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.core.identity.service.EnhanceUserDetailsService;
import com.pigx.engine.data.core.enums.DataItemStatus;
import com.pigx.engine.message.core.definition.strategy.AccountStatusChangedEventManager;
import com.pigx.engine.message.core.domain.AccountStatus;
import com.pigx.engine.oauth2.extension.stamp.LockedAccountStampManager;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Component
public class OAuth2AccountStatusManager {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AccountStatusManager.class);

    private final UserDetailsService userDetailsService;
    private final AccountStatusChangedEventManager accountStatusChangedEventManager;
    private final LockedAccountStampManager lockedAccountStampManager;

    public OAuth2AccountStatusManager(UserDetailsService userDetailsService, AccountStatusChangedEventManager accountStatusChangedEventManager, LockedAccountStampManager lockedAccountStampManager) {
        this.userDetailsService = userDetailsService;
        this.lockedAccountStampManager = lockedAccountStampManager;
        this.accountStatusChangedEventManager = accountStatusChangedEventManager;
    }

    private EnhanceUserDetailsService getUserDetailsService() {
        return (EnhanceUserDetailsService) userDetailsService;
    }

    private String getUserId(String username) {
        EnhanceUserDetailsService enhanceUserDetailsService = getUserDetailsService();
        HerodotusUser user = enhanceUserDetailsService.loadHerodotusUserByUsername(username);
        if (ObjectUtils.isNotEmpty(user)) {
            return user.getUserId();
        }

        log.warn("[PIGXD] |- Can not found the userid for [{}]", username);
        return null;
    }

    public void lock(String username) {
        String userId = getUserId(username);
        if (ObjectUtils.isNotEmpty(userId)) {
            log.debug("[PIGXD] |- [A1] Start disable account status changed process for [{}]", userId);
            accountStatusChangedEventManager.postProcess(new AccountStatus(userId, DataItemStatus.LOCKING.name()));
            lockedAccountStampManager.put(userId, username);
            log.info("[PIGXD] |- User count [{}] has been locked, and record into cache!", username);
        }
    }

    public void enable(String userId) {
        if (ObjectUtils.isNotEmpty(userId)) {
            log.debug("[PIGXD] |- [A1] Start enable account status changed process for [{}]", userId);
            accountStatusChangedEventManager.postProcess(new AccountStatus(userId, DataItemStatus.ENABLE.name()));
        }
    }

    public void releaseFromCache(String username) {
        String userId = getUserId(username);
        if (ObjectUtils.isNotEmpty(userId)) {
            String value = lockedAccountStampManager.get(userId);
            if (StringUtils.isNotEmpty(value)) {
                this.lockedAccountStampManager.delete(userId);
                log.info("[PIGXD] |- User count [{}] locked info has been release!", username);
            }
        }
    }
}
