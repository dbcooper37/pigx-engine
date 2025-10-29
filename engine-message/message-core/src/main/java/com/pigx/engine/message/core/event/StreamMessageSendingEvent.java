package com.pigx.engine.message.core.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.StreamMessage;

import java.time.Clock;


public class StreamMessageSendingEvent extends AbstractApplicationEvent<StreamMessage> {

    public StreamMessageSendingEvent(StreamMessage data) {
        super(data);
    }

    public StreamMessageSendingEvent(StreamMessage data, Clock clock) {
        super(data, clock);
    }
}
