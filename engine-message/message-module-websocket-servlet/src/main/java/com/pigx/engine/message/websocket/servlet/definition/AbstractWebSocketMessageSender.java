package com.pigx.engine.message.websocket.servlet.definition;

import com.pigx.engine.message.websocket.servlet.messaging.WebSocketMessagingTemplate;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/definition/AbstractWebSocketMessageSender.class */
public abstract class AbstractWebSocketMessageSender implements WebSocketMessageSender {
    private final WebSocketMessagingTemplate webSocketMessagingTemplate;

    protected AbstractWebSocketMessageSender(WebSocketMessagingTemplate webSocketMessagingTemplate) {
        this.webSocketMessagingTemplate = webSocketMessagingTemplate;
    }

    @Override // com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender
    public void toUser(String user, String destination, Object payload) {
        this.webSocketMessagingTemplate.pointToPoint(user, destination, payload);
    }

    @Override // com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender
    public void toAll(String destination, Object payload) {
        this.webSocketMessagingTemplate.broadcast(destination, payload);
    }
}
