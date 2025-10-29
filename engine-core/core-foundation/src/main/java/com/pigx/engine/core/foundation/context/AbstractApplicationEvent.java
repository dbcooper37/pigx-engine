package com.pigx.engine.core.foundation.context;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;


public abstract class AbstractApplicationEvent<T> extends ApplicationEvent {

    private final T data;

    public AbstractApplicationEvent(T data) {
        super(data);
        this.data = data;
    }

    public AbstractApplicationEvent(T data, Clock clock) {
        super(data, clock);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
