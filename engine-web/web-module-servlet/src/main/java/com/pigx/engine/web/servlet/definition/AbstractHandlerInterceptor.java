package com.pigx.engine.web.servlet.definition;

import cn.hutool.v7.crypto.SecureUtil;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;


public abstract class AbstractHandlerInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AbstractHandlerInterceptor.class);

    protected String generateRequestKey(HttpServletRequest request) {

        String sessionId = SessionUtils.analyseSessionId(request);

        String url = request.getRequestURI();
        String method = request.getMethod();

        if (StringUtils.isNotBlank(sessionId)) {
            String key = SecureUtil.md5(sessionId + SymbolConstants.COLON + url + SymbolConstants.COLON + method);
            log.debug("[PIGXD] |- IdempotentInterceptor key is [{}].", key);
            return key;
        } else {
            log.warn("[PIGXD] |- IdempotentInterceptor cannot create key, because sessionId is null.");
            return null;
        }
    }
}
