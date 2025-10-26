package com.pigx.engine.message.core.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.StreamMessage;
import java.time.Clock;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/event/StreamMessageSendingEvent.class */
public class StreamMessageSendingEvent extends AbstractApplicationEvent<StreamMessage> {
    public StreamMessageSendingEvent(StreamMessage data) {
        super(data);
    }

    public StreamMessageSendingEvent(StreamMessage data, Clock clock) {
        super(data, clock);
    }
}
