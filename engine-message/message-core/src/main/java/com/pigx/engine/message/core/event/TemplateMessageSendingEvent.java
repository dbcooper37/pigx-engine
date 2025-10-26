package com.pigx.engine.message.core.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.TemplateMessage;
import java.time.Clock;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/event/TemplateMessageSendingEvent.class */
public class TemplateMessageSendingEvent<T extends TemplateMessage> extends AbstractApplicationEvent<T> {
    public TemplateMessageSendingEvent(T data) {
        super(data);
    }

    public TemplateMessageSendingEvent(T data, Clock clock) {
        super(data, clock);
    }
}
