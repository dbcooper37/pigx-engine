package com.pigx.engine.rest.servlet.identity.dto;

import com.google.common.base.MoreObjects;

/* loaded from: rest-module-servlet-identity-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/identity/dto/Session.class */
public class Session extends SessionExchange {
    private String state;

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override // com.pigx.engine.rest.servlet.identity.dto.SessionExchange
    public String toString() {
        return MoreObjects.toStringHelper(this).add("state", this.state).toString();
    }
}
