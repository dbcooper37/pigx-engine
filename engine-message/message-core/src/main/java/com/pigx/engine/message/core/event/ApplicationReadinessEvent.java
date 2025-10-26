package com.pigx.engine.message.core.event;

import java.time.Clock;
import org.springframework.context.ApplicationEvent;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/event/ApplicationReadinessEvent.class */
public class ApplicationReadinessEvent extends ApplicationEvent {
    public ApplicationReadinessEvent(Object source) {
        super(source);
    }

    public ApplicationReadinessEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
