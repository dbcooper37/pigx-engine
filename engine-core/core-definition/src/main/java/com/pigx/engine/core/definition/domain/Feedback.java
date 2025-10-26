package com.pigx.engine.core.definition.domain;

import cn.hutool.v7.core.lang.Assert;
import com.google.common.base.Objects;
import java.io.Serializable;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/Feedback.class */
public class Feedback implements Serializable, BaseDomain {
    private static final int IS_NOT_CUSTOMIZED = 0;
    private final String message;
    private final int status;
    private final int custom;

    public Feedback(String message, int status) {
        this(message, status, 0);
    }

    public Feedback(String message, int status, int custom) {
        Assert.checkBetween(custom, 0, 9);
        this.message = message;
        this.status = status;
        this.custom = custom;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return this.status;
    }

    public boolean isCustom() {
        return this.custom != 0;
    }

    public int getCustom() {
        return this.custom;
    }

    public int getSequence() {
        if (isCustom()) {
            return this.custom * 10000;
        }
        return this.status * 100;
    }

    public int getSequence(int index) {
        return getSequence() + index;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feedback feedback = (Feedback) o;
        return Objects.equal(this.message, feedback.message);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.message});
    }
}
