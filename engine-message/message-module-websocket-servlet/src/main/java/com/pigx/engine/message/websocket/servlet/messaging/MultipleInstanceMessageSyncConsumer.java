package com.pigx.engine.message.websocket.servlet.messaging;

import com.pigx.engine.message.core.constants.MessageConstants;
import com.pigx.engine.message.core.domain.WebSocketMessage;
import org.apache.commons.lang3.Strings;

import java.util.function.Consumer;


public class MultipleInstanceMessageSyncConsumer implements Consumer<WebSocketMessage> {

    private final WebSocketMessagingTemplate webSocketMessagingTemplate;

    public MultipleInstanceMessageSyncConsumer(WebSocketMessagingTemplate webSocketMessagingTemplate) {
        this.webSocketMessagingTemplate = webSocketMessagingTemplate;
    }

    @Override
    public void accept(WebSocketMessage webSocketMessage) {
        if (Strings.CS.equals(webSocketMessage.getUser(), MessageConstants.MESSAGE_TO_ALL)) {
            webSocketMessagingTemplate.broadcast(webSocketMessage.getDestination(), webSocketMessage.getPayload());
        } else {
            webSocketMessagingTemplate.pointToPoint(webSocketMessage.getUser(), webSocketMessage.getDestination(), webSocketMessage.getPayload());
        }
    }
}
