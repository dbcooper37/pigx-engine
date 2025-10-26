package com.pigx.engine.message.core.domain;

import com.pigx.engine.core.definition.domain.BaseModel;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/domain/AccountStatus.class */
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
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountStatus that = (AccountStatus) o;
        return Objects.equal(this.userId, that.userId) && Objects.equal(this.status, that.status);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.userId, this.status});
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("userId", this.userId).add("status", this.status).toString();
    }
}
