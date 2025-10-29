package com.pigx.engine.message.websocket.servlet.definition;

import com.pigx.engine.message.websocket.servlet.messaging.WebSocketMessagingTemplate;


public abstract class AbstractWebSocketMessageSender implements WebSocketMessageSender {

    private final WebSocketMessagingTemplate webSocketMessagingTemplate;

    protected AbstractWebSocketMessageSender(WebSocketMessagingTemplate webSocketMessagingTemplate) {
        this.webSocketMessagingTemplate = webSocketMessagingTemplate;
    }

    @Override
    public void toUser(String user, String destination, Object payload) {
        webSocketMessagingTemplate.pointToPoint(user, destination, payload);
    }

    @Override
    public void toAll(String destination, Object payload) {
        webSocketMessagingTemplate.broadcast(destination, payload);
    }
}
