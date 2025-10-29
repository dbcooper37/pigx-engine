package com.pigx.engine.message.core.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.pigx.engine.core.definition.domain.BaseModel;


public class AccountStatus implements BaseModel {


    private String userId;

    private String status;

    public AccountStatus() {
    }

    public AccountStatus(String userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountStatus that = (AccountStatus) o;
        return Objects.equal(userId, that.userId) && Objects.equal(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, status);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("status", status)
                .toString();
    }
}
