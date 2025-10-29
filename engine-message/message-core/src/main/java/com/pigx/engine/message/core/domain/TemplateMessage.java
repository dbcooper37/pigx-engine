package com.pigx.engine.message.core.domain;

import com.google.common.base.MoreObjects;


public class TemplateMessage implements Message {

    private String destination;

    private Object payload;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("destination", destination)
                .add("payload", payload)
                .toString();
    }
}
