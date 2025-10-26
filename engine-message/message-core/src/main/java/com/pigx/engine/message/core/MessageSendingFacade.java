package com.pigx.engine.message.core;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.message.core.domain.StreamMessage;
import com.pigx.engine.message.core.domain.TemplateMessage;
import com.pigx.engine.message.core.domain.WebSocketMessage;
import com.pigx.engine.message.core.event.StreamMessageSendingEvent;
import com.pigx.engine.message.core.event.TemplateMessageSendingEvent;
import org.apache.commons.lang3.StringUtils;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/MessageSendingFacade.class */
class MessageSendingFacade {
    MessageSendingFacade() {
    }

    private static <T> void postProcess(AbstractApplicationEvent<T> event) {
        ServiceContextHolder.publishEvent(event);
    }

    public static <T> void event(AbstractApplicationEvent<T> event) {
        postProcess(event);
    }

    public static <T extends TemplateMessage> void template(T message) {
        postProcess(new TemplateMessageSendingEvent(message));
    }

    public static <T extends StreamMessage> void stream(T message) {
        postProcess(new StreamMessageSendingEvent(message));
    }

    public static <T> void pointToPoint(String user, String destination, T payload) {
        WebSocketMessage message = new WebSocketMessage();
        if (StringUtils.isNotBlank(user)) {
            message.setUser(user);
        }
        message.setDestination(destination);
        message.setPayload(payload);
        template(message);
    }

    public static <T> void broadcast(String destination, T payload) {
        pointToPoint(null, destination, payload);
    }
}
