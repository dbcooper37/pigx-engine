package com.pigx.engine.core.definition.domain.view.datatables;

import com.google.common.base.MoreObjects;
import java.io.Serializable;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/view/datatables/DataTableParameter.class */
public class DataTableParameter implements Serializable {
    private String name;
    private Object value;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", this.name).add("value", this.value).toString();
    }
}
