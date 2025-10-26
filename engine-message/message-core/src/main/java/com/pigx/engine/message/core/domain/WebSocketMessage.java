package com.pigx.engine.message.core.domain;

import com.google.common.base.MoreObjects;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/domain/WebSocketMessage.class */
public class WebSocketMessage extends TemplateMessage {
    private String user;

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override // com.pigx.engine.message.core.domain.TemplateMessage
    public String toString() {
        return MoreObjects.toStringHelper(this).add("user", this.user).toString();
    }
}
