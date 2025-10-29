package com.pigx.engine.message.websocket.servlet.messaging;

import com.pigx.engine.message.core.definition.MessageSendingAdapter;
import com.pigx.engine.message.core.domain.WebSocketMessage;
import com.pigx.engine.message.core.event.TemplateMessageSendingEvent;
import com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender;
import org.apache.commons.lang3.StringUtils;


public class WebSocketMessageSendingAdapter implements MessageSendingAdapter<WebSocketMessage, TemplateMessageSendingEvent<WebSocketMessage>> {

    private final WebSocketMessageSender webSocketMessageSender;

    public WebSocketMessageSendingAdapter(WebSocketMessageSender webSocketMessageSender) {
        this.webSocketMessageSender = webSocketMessageSender;
    }


    @Override
    public void onApplicationEvent(TemplateMessageSendingEvent<WebSocketMessage> event) {
        WebSocketMessage message = event.getData();

        if (StringUtils.isNotBlank(message.getUser())) {
            webSocketMessageSender.toUser(message.getUser(), message.getDestination(), message.getPayload());
        } else {
            webSocketMessageSender.toAll(message.getDestination(), message.getPayload());
        }
    }
}
