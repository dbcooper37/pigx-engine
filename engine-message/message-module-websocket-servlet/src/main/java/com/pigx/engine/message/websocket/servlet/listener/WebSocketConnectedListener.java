package com.pigx.engine.message.websocket.servlet.listener;

import com.pigx.engine.message.websocket.servlet.definition.AbstractWebSocketStatusListener;
import com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender;
import com.pigx.engine.message.websocket.servlet.domain.WebSocketPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;


@Component
public class WebSocketConnectedListener extends AbstractWebSocketStatusListener<SessionConnectedEvent> {

    public WebSocketConnectedListener(WebSocketMessageSender webSocketMessageSender) {
        super(webSocketMessageSender);
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        WebSocketPrincipal principal = (WebSocketPrincipal) event.getUser();

        connected(principal);
    }
}
