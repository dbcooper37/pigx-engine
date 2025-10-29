package com.pigx.engine.core.definition.domain.view.vue;

import com.google.common.base.MoreObjects;

import java.io.Serializable;


public class Option implements Serializable {

    private String label;

    private String value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("label", label)
                .add("value", value)
                .toString();
    }
}
