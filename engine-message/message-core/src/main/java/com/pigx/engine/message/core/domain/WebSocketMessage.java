package com.pigx.engine.message.core.domain;

import com.google.common.base.MoreObjects;


public class WebSocketMessage extends TemplateMessage {

    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("user", user)
                .toString();
    }
}
