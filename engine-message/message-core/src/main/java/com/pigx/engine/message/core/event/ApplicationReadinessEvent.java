package com.pigx.engine.message.core.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;


public class ApplicationReadinessEvent extends ApplicationEvent {

    public ApplicationReadinessEvent(Object source) {
        super(source);
    }

    public ApplicationReadinessEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
