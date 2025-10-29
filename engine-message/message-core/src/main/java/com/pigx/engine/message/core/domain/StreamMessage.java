package com.pigx.engine.message.core.domain;

import com.google.common.base.MoreObjects;
import org.springframework.util.MimeType;

import java.io.Serializable;


public class StreamMessage implements Serializable {

    private String bindingName;
    private String binderName;
    private Object payload;
    private MimeType outputContentType;

    public String getBindingName() {
        return bindingName;
    }

    public void setBindingName(String bindingName) {
        this.bindingName = bindingName;
    }

    public String getBinderName() {
        return binderName;
    }

    public void setBinderName(String binderName) {
        this.binderName = binderName;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public MimeType getOutputContentType() {
        return outputContentType;
    }

    public void setOutputContentType(MimeType outputContentType) {
        this.outputContentType = outputContentType;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bindingName", bindingName)
                .add("binderName", binderName)
                .add("payload", payload)
                .add("outputContentType", outputContentType)
                .toString();
    }
}
