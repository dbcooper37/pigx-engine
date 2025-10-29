package com.pigx.engine.message.websocket.servlet.interceptor;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import com.pigx.engine.message.websocket.servlet.domain.WebSocketPrincipal;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;


public class WebSocketPrincipalHandshakeHandler extends DefaultHandshakeHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketPrincipalHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        Object object = attributes.get(SystemConstants.PRINCIPAL);

        if (ObjectUtils.isNotEmpty(object) && object instanceof UserPrincipal details) {
            WebSocketPrincipal webSocketPrincipal = new WebSocketPrincipal(details);
            log.debug("[PIGXD] |- Determine user by request parameter, userId is  [{}].", webSocketPrincipal.getUserId());
            return webSocketPrincipal;
        }

        Principal principal = request.getPrincipal();
        if (ObjectUtils.isNotEmpty(principal)) {
            log.debug("[PIGXD] |- Determine user from request, value is  [{}].", principal.getName());
            return principal;
        }

        log.warn("[PIGXD] |- Can not determine user from request.");
        return null;
    }
}
