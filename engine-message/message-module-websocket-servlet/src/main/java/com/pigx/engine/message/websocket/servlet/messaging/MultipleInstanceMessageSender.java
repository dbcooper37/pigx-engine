package com.pigx.engine.message.websocket.servlet.messaging;

import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.message.core.constants.MessageConstants;
import com.pigx.engine.message.core.domain.StreamMessage;
import com.pigx.engine.message.core.domain.WebSocketMessage;
import com.pigx.engine.message.core.event.StreamMessageSendingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/messaging/MultipleInstanceMessageSender.class */
public class MultipleInstanceMessageSender extends SingleInstanceMessageSender {
    private static final Logger log = LoggerFactory.getLogger(MultipleInstanceMessageSender.class);

    public MultipleInstanceMessageSender(WebSocketMessagingTemplate webSocketMessagingTemplate) {
        super(webSocketMessagingTemplate);
    }

    @Override // com.pigx.engine.message.websocket.servlet.definition.AbstractWebSocketMessageSender, com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender
    public void toUser(String user, String destination, Object payload) {
        syncToUserMessage(user, destination, payload);
        super.toUser(user, destination, payload);
    }

    @Override // com.pigx.engine.message.websocket.servlet.definition.AbstractWebSocketMessageSender, com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender
    public void toAll(String destination, Object payload) {
        syncToAllMessage(destination, payload);
        super.toAll(destination, payload);
    }

    private void syncMessageToOtherInstants(WebSocketMessage webSocketMessage) {
        StreamMessage streamMessage = new StreamMessage();
        streamMessage.setBindingName(MessageConstants.MULTIPLE_INSTANCE_OUTPUT);
        streamMessage.setPayload(webSocketMessage);
        log.debug("[Herodotus] |- Sync message to other WebSocket instance.");
        ServiceContextHolder.publishEvent(new StreamMessageSendingEvent(streamMessage));
    }

    private void syncToUserMessage(String user, String destination, Object payload) {
        WebSocketMessage webSocketMessage = new WebSocketMessage();
        webSocketMessage.setUser(user);
        webSocketMessage.setDestination(destination);
        webSocketMessage.setPayload(payload);
        syncMessageToOtherInstants(webSocketMessage);
    }

    private void syncToAllMessage(String destination, Object payload) {
        syncToUserMessage(MessageConstants.MESSAGE_TO_ALL, destination, payload);
    }
}
