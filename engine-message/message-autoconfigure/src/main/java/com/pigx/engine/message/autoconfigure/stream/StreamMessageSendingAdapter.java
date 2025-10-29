package com.pigx.engine.message.autoconfigure.stream;

import com.pigx.engine.message.core.domain.StreamMessage;
import com.pigx.engine.message.core.event.StreamMessageSendingEvent;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.ApplicationListener;

public class StreamMessageSendingAdapter implements ApplicationListener<StreamMessageSendingEvent> {
    private static final Logger log = LoggerFactory.getLogger(StreamMessageSendingAdapter.class);
    private final StreamBridge streamBridge;

    public StreamMessageSendingAdapter(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void onApplicationEvent(StreamMessageSendingEvent event) {
        StreamMessage message = event.getData();
        log.debug("[PIGXD-ENGINE] |- [M4] Stream start to handle received message!");
        if (ObjectUtils.isEmpty(message.getBinderName())) {
            if (ObjectUtils.isEmpty(message.getOutputContentType())) {
                this.streamBridge.send(message.getBindingName(), message.getBinderName(), message.getPayload());
                return;
            } else {
                this.streamBridge.send(message.getBindingName(), message.getBinderName(), message.getPayload(), message.getOutputContentType());
                return;
            }
        }
        if (ObjectUtils.isEmpty(message.getOutputContentType())) {
            this.streamBridge.send(message.getBindingName(), message.getPayload());
        } else {
            this.streamBridge.send(message.getBindingName(), message.getPayload(), message.getOutputContentType());
        }
    }
}
