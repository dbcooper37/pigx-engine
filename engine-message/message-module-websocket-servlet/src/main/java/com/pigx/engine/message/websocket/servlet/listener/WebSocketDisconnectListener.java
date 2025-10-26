package com.pigx.engine.message.websocket.servlet.listener;

import com.pigx.engine.message.websocket.servlet.definition.AbstractWebSocketStatusListener;
import com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender;
import com.pigx.engine.message.websocket.servlet.domain.WebSocketPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/listener/WebSocketDisconnectListener.class */
public class WebSocketDisconnectListener extends AbstractWebSocketStatusListener<SessionDisconnectEvent> {
    public WebSocketDisconnectListener(WebSocketMessageSender webSocketMessageSender) {
        super(webSocketMessageSender);
    }

    public void onApplicationEvent(SessionDisconnectEvent event) {
        WebSocketPrincipal principal = (WebSocketPrincipal) event.getUser();
        disconnected(principal);
    }
}
