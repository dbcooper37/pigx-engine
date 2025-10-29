package com.pigx.engine.core.definition.domain.view.datatables;

import com.google.common.base.MoreObjects;

import java.io.Serializable;


public class DataTableParameter implements Serializable {

    private String name;
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("value", value)
                .toString();
    }
}
