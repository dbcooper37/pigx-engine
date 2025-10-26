package com.pigx.engine.message.websocket.servlet.messaging;

import com.pigx.engine.message.core.constants.MessageConstants;
import com.pigx.engine.message.core.domain.WebSocketMessage;
import java.util.function.Consumer;
import org.apache.commons.lang3.Strings;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/messaging/MultipleInstanceMessageSyncConsumer.class */
public class MultipleInstanceMessageSyncConsumer implements Consumer<WebSocketMessage> {
    private final WebSocketMessagingTemplate webSocketMessagingTemplate;

    public MultipleInstanceMessageSyncConsumer(WebSocketMessagingTemplate webSocketMessagingTemplate) {
        this.webSocketMessagingTemplate = webSocketMessagingTemplate;
    }

    @Override // java.util.function.Consumer
    public void accept(WebSocketMessage webSocketMessage) {
        if (Strings.CS.equals(webSocketMessage.getUser(), MessageConstants.MESSAGE_TO_ALL)) {
            this.webSocketMessagingTemplate.broadcast(webSocketMessage.getDestination(), webSocketMessage.getPayload());
        } else {
            this.webSocketMessagingTemplate.pointToPoint(webSocketMessage.getUser(), webSocketMessage.getDestination(), webSocketMessage.getPayload());
        }
    }
}
