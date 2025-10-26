package com.pigx.engine.oauth2.extension.dto;

import com.pigx.engine.core.definition.domain.BaseDto;

/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/dto/SignInErrorStatus.class */
public class SignInErrorStatus implements BaseDto {
    private int errorTimes;
    private int remainTimes;
    private Boolean locked;

    public int getErrorTimes() {
        return this.errorTimes;
    }

    public void setErrorTimes(int errorTimes) {
        this.errorTimes = errorTimes;
    }

    public int getRemainTimes() {
        return this.remainTimes;
    }

    public void setRemainTimes(int remainTimes) {
        this.remainTimes = remainTimes;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
