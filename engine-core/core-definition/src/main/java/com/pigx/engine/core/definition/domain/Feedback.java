package com.pigx.engine.core.definition.domain;

import cn.hutool.v7.core.lang.Assert;
import com.google.common.base.Objects;

import java.io.Serializable;


public non-sealed class Feedback implements Serializable, BaseDomain {

    private static final int IS_NOT_CUSTOMIZED = 0;

    private final String message;
    private final int status;
    /**
     * 实际错误码如果与 HttpStatus 错误码对应，即开头数字为 1~5；自定义错误码，开头数字为 6~9。
     * 为了方便区分错误码是与 HttpStatus 错误码对应的还是自定义的，增加了 custom 属性。如果 custom 为 0，即为与 HttpStatus 错误码对应；如果为 6~9 那么就代表是自定义错误码
     */
    private final int custom;

    public Feedback(String message, int status) {
        this(message, status, IS_NOT_CUSTOMIZED);
    }

    public Feedback(String message, int status, int custom) {
        Assert.checkBetween(custom, IS_NOT_CUSTOMIZED, 9);
        this.message = message;
        this.status = status;
        this.custom = custom;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public boolean isCustom() {
        return custom != IS_NOT_CUSTOMIZED;
    }

    public int getCustom() {
        return custom;
    }

    public int getSequence() {
        if (isCustom()) {
            return custom * 10000;
        } else {
            return status * 100;
        }
    }

    public int getSequence(int index) {
        return getSequence() + index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Feedback feedback = (Feedback) o;
        return Objects.equal(message, feedback.message);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message);
    }
}
