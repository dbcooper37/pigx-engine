package com.pigx.engine.message.core.domain;

import com.google.common.base.MoreObjects;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/domain/TemplateMessage.class */
public class TemplateMessage implements Message {
    private String destination;
    private Object payload;

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Object getPayload() {
        return this.payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("destination", this.destination).add("payload", this.payload).toString();
    }
}
