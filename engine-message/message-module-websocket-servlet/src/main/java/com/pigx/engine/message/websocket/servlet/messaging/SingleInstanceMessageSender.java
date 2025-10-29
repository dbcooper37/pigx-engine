package com.pigx.engine.message.websocket.servlet.messaging;

import com.pigx.engine.message.websocket.servlet.definition.AbstractWebSocketMessageSender;


public class SingleInstanceMessageSender extends AbstractWebSocketMessageSender {
    public SingleInstanceMessageSender(WebSocketMessagingTemplate webSocketMessagingTemplate) {
        super(webSocketMessagingTemplate);
    }
}
