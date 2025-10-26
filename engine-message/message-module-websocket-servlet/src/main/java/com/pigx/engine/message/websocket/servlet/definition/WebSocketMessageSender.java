package com.pigx.engine.message.websocket.servlet.definition;

import com.pigx.engine.message.core.constants.MessageConstants;
import com.pigx.engine.message.core.domain.WebSocketMessage;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/definition/WebSocketMessageSender.class */
public interface WebSocketMessageSender {
    void toUser(String user, String destination, Object payload);

    void toAll(String destination, Object payload);

    default void toUser(WebSocketMessage webSocketMessage) {
        toUser(webSocketMessage.getUser(), webSocketMessage.getDestination(), webSocketMessage.getPayload());
    }

    default void announcement(Object payload) {
        toAll(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_NOTICE, payload);
    }

    default void online(Object payload) {
        toAll(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_ONLINE, payload);
    }
}
