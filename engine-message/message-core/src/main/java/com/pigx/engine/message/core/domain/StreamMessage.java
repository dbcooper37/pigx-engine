package com.pigx.engine.message.core.domain;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import org.springframework.util.MimeType;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/domain/StreamMessage.class */
public class StreamMessage implements Serializable {
    private String bindingName;
    private String binderName;
    private Object payload;
    private MimeType outputContentType;

    public String getBindingName() {
        return this.bindingName;
    }

    public void setBindingName(String bindingName) {
        this.bindingName = bindingName;
    }

    public String getBinderName() {
        return this.binderName;
    }

    public void setBinderName(String binderName) {
        this.binderName = binderName;
    }

    public Object getPayload() {
        return this.payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public MimeType getOutputContentType() {
        return this.outputContentType;
    }

    public void setOutputContentType(MimeType outputContentType) {
        this.outputContentType = outputContentType;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("bindingName", this.bindingName).add("binderName", this.binderName).add("payload", this.payload).add("outputContentType", this.outputContentType).toString();
    }
}
