package com.pigx.engine.message.websocket.servlet.definition;

import com.pigx.engine.cache.redis.utils.RedisBitMapUtils;
import com.pigx.engine.message.core.constants.MessageConstants;
import com.pigx.engine.message.websocket.servlet.domain.WebSocketPrincipal;
import com.pigx.engine.message.websocket.servlet.utils.WebSocketUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;


public abstract class AbstractWebSocketStatusListener<E extends ApplicationEvent> implements ApplicationListener<E> {

    private static final Logger log = LoggerFactory.getLogger(AbstractWebSocketStatusListener.class);

    private final WebSocketMessageSender webSocketMessageSender;

    public AbstractWebSocketStatusListener(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }

    private void changeStatus(WebSocketPrincipal principal, boolean isOnline) {
        if (ObjectUtils.isNotEmpty(principal)) {

            RedisBitMapUtils.setBit(MessageConstants.REDIS_CURRENT_ONLINE_USER, principal.getName(), isOnline);

            String status = isOnline ? "Online" : "Offline";

            log.debug("[PIGXD] |- WebSocket user [{}] is [{}].", principal, status);

            int count = WebSocketUtils.getOnlineCount();

            webSocketMessageSender.online(count);
        }
    }

    protected void connected(WebSocketPrincipal principal) {
        changeStatus(principal, true);
    }

    protected void disconnected(WebSocketPrincipal principal) {
        changeStatus(principal, false);
    }
}
