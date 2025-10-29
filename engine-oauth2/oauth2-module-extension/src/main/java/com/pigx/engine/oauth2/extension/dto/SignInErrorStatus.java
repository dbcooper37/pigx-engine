package com.pigx.engine.oauth2.extension.dto;

import com.pigx.engine.core.definition.domain.BaseDto;


public class SignInErrorStatus implements BaseDto {

    private int errorTimes;
    private int remainTimes;
    private Boolean locked;

    public int getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(int errorTimes) {
        this.errorTimes = errorTimes;
    }

    public int getRemainTimes() {
        return remainTimes;
    }

    public void setRemainTimes(int remainTimes) {
        this.remainTimes = remainTimes;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
}
