package com.pigx.engine.message.websocket.servlet.listener;

import com.pigx.engine.message.websocket.servlet.definition.AbstractWebSocketStatusListener;
import com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender;
import com.pigx.engine.message.websocket.servlet.domain.WebSocketPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Component
public class WebSocketDisconnectListener extends AbstractWebSocketStatusListener<SessionDisconnectEvent> {

    public WebSocketDisconnectListener(WebSocketMessageSender webSocketMessageSender) {
        super(webSocketMessageSender);
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        WebSocketPrincipal principal = (WebSocketPrincipal) event.getUser();

        disconnected(principal);
    }
}
