package com.pigx.engine.oauth2.extension.listener;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import com.pigx.engine.oauth2.extension.manager.OAuth2ComplianceManager;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/listener/AutoUnlockAccountListener.class */
public class AutoUnlockAccountListener extends KeyExpirationEventMessageListener {
    private static final Logger log = LoggerFactory.getLogger(AutoUnlockAccountListener.class);
    private final OAuth2ComplianceManager complianceManager;

    public AutoUnlockAccountListener(RedisMessageListenerContainer listenerContainer, OAuth2ComplianceManager complianceManager) {
        super(listenerContainer);
        this.complianceManager = complianceManager;
    }

    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        if (Strings.CS.contains(key, OAuth2Constants.CACHE_NAME_TOKEN_LOCKED_USER_DETAIL)) {
            String userId = StringUtils.substringAfterLast(key, SymbolConstants.COLON);
            log.info("[Herodotus] |- Parse the user [{}] at expired redis cache key [{}]", userId, key);
            if (StringUtils.isNotBlank(userId)) {
                log.debug("[Herodotus] |- Automatically unlock user account [{}]", userId);
                this.complianceManager.enable(userId);
            }
        }
    }
}
