package com.pigx.engine.message.websocket.servlet.messaging;

import com.pigx.engine.message.core.definition.MessageSendingAdapter;
import com.pigx.engine.message.core.domain.WebSocketMessage;
import com.pigx.engine.message.core.event.TemplateMessageSendingEvent;
import com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender;
import org.apache.commons.lang3.StringUtils;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/messaging/WebSocketMessageSendingAdapter.class */
public class WebSocketMessageSendingAdapter implements MessageSendingAdapter<WebSocketMessage, TemplateMessageSendingEvent<WebSocketMessage>> {
    private final WebSocketMessageSender webSocketMessageSender;

    public WebSocketMessageSendingAdapter(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }

    public void onApplicationEvent(TemplateMessageSendingEvent<WebSocketMessage> event) {
        WebSocketMessage message = event.getData();
        if (StringUtils.isNotBlank(message.getUser())) {
            this.webSocketMessageSender.toUser(message.getUser(), message.getDestination(), message.getPayload());
        } else {
            this.webSocketMessageSender.toAll(message.getDestination(), message.getPayload());
        }
    }
}
