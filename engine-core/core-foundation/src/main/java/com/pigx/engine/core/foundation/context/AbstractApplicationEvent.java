package com.pigx.engine.core.foundation.context;

import java.time.Clock;
import org.springframework.context.ApplicationEvent;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/context/AbstractApplicationEvent.class */
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
        return this.data;
    }
}
