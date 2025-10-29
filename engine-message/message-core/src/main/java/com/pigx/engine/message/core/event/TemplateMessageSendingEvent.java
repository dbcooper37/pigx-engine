package com.pigx.engine.message.core.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.TemplateMessage;

import java.time.Clock;


public class TemplateMessageSendingEvent<T extends TemplateMessage> extends AbstractApplicationEvent<T> {
    public TemplateMessageSendingEvent(T data) {
        super(data);
    }

    public TemplateMessageSendingEvent(T data, Clock clock) {
        super(data, clock);
    }
}
