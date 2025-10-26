package com.pigx.engine.message.websocket.servlet.listener;

import com.pigx.engine.message.websocket.servlet.definition.AbstractWebSocketStatusListener;
import com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender;
import com.pigx.engine.message.websocket.servlet.domain.WebSocketPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/listener/WebSocketConnectedListener.class */
public class WebSocketConnectedListener extends AbstractWebSocketStatusListener<SessionConnectedEvent> {
    public WebSocketConnectedListener(WebSocketMessageSender webSocketMessageSender) {
        super(webSocketMessageSender);
    }

    public void onApplicationEvent(SessionConnectedEvent event) {
        WebSocketPrincipal principal = (WebSocketPrincipal) event.getUser();
        connected(principal);
    }
}
