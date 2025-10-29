package com.pigx.engine.message.core.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.MqttMessage;

import java.time.Clock;


public class MqttMessageSendingEvent extends AbstractApplicationEvent<MqttMessage> {

    public MqttMessageSendingEvent(MqttMessage data) {
        super(data);
    }

    public MqttMessageSendingEvent(MqttMessage data, Clock clock) {
        super(data, clock);
    }
}
