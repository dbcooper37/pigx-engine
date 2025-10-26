package com.pigx.engine.message.core.domain;

import com.google.common.base.MoreObjects;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/domain/MqttMessage.class */
public class MqttMessage implements Message {
    private String topic;
    private String responseTopic;
    private String correlationData;
    private Integer qos;
    private String payload;

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getResponseTopic() {
        return this.responseTopic;
    }

    public void setResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
    }

    public String getCorrelationData() {
        return this.correlationData;
    }

    public void setCorrelationData(String correlationData) {
        this.correlationData = correlationData;
    }

    public Integer getQos() {
        return this.qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }

    public String getPayload() {
        return this.payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("topic", this.topic).add("responseTopic", this.responseTopic).add("correlationData", this.correlationData).add("qos", this.qos).add("payload", this.payload).toString();
    }
}
