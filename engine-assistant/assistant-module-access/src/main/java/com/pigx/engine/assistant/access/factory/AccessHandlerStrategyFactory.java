package com.pigx.engine.assistant.access.factory;

import com.pigx.engine.assistant.access.definition.AccessHandler;
import com.pigx.engine.assistant.access.definition.domain.AccessResponse;
import com.pigx.engine.assistant.access.definition.domain.AccessUserDetails;
import com.pigx.engine.assistant.access.exception.AccessHandlerNotFoundException;
import com.pigx.engine.assistant.access.exception.IllegalAccessArgumentException;
import com.pigx.engine.core.identity.domain.AccessPrincipal;
import com.pigx.engine.core.identity.enums.AccountCategory;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/factory/AccessHandlerStrategyFactory.class */
public class AccessHandlerStrategyFactory {
    private static final Logger log = LoggerFactory.getLogger(AccessHandlerStrategyFactory.class);
    private final Map<String, AccessHandler> handlers;

    public AccessHandlerStrategyFactory(Map<String, AccessHandler> handlers) {
        this.handlers = handlers;
    }

    public AccessResponse preProcess(String source, String core, String... params) {
        AccessHandler socialAuthenticationHandler = getAccessHandler(source);
        return socialAuthenticationHandler.preProcess(core, params);
    }

    public AccessResponse preProcess(AccountCategory accountCategory, String core, String... params) {
        AccessHandler socialAuthenticationHandler = getAccessHandler(accountCategory);
        return socialAuthenticationHandler.preProcess(core, params);
    }

    public AccessUserDetails findAccessUserDetails(String source, AccessPrincipal accessPrincipal) {
        AccessHandler socialAuthenticationHandler = getAccessHandler(source);
        AccessUserDetails accessUserDetails = socialAuthenticationHandler.loadUserDetails(source, accessPrincipal);
        log.debug("[Herodotus] |- AccessHandlerFactory findAccessUserDetails.");
        return accessUserDetails;
    }

    public AccessHandler getAccessHandler(String source) {
        if (ObjectUtils.isEmpty(source)) {
            throw new IllegalAccessArgumentException("Cannot found SocialProvider");
        }
        AccountCategory accountCategory = AccountCategory.getAccountType(source);
        if (ObjectUtils.isEmpty(accountCategory)) {
            throw new IllegalAccessArgumentException("Cannot parse the source parameter.");
        }
        return getAccessHandler(accountCategory);
    }

    public AccessHandler getAccessHandler(AccountCategory accountCategory) {
        String handlerName = accountCategory.getHandler();
        AccessHandler socialAuthenticationHandler = this.handlers.get(handlerName);
        if (ObjectUtils.isNotEmpty(socialAuthenticationHandler)) {
            return socialAuthenticationHandler;
        }
        throw new AccessHandlerNotFoundException("Can not found Social Handler for " + handlerName);
    }
}
