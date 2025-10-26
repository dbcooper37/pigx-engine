package com.pigx.engine.message.websocket.servlet.domain;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.security.Principal;
import java.util.Set;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/domain/WebSocketPrincipal.class */
public class WebSocketPrincipal implements Principal {
    private String userId;
    private String username;
    private String employeeId;
    private String avatar;
    private Set<String> roles;

    public WebSocketPrincipal(UserPrincipal details) {
        this.userId = details.getId();
        this.username = details.getName();
        this.employeeId = details.getEmployeeId();
        this.avatar = details.getAvatar();
        this.roles = details.getRoles();
    }

    public WebSocketPrincipal(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override // java.security.Principal
    public String getName() {
        return this.userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override // java.security.Principal
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSocketPrincipal that = (WebSocketPrincipal) o;
        return Objects.equal(this.userId, that.userId);
    }

    @Override // java.security.Principal
    public int hashCode() {
        return Objects.hashCode(new Object[]{this.userId});
    }

    @Override // java.security.Principal
    public String toString() {
        return MoreObjects.toStringHelper(this).add("userId", this.userId).add(SystemConstants.USERNAME, this.username).add(SystemConstants.EMPLOYEE_ID, this.employeeId).add(SystemConstants.AVATAR, this.avatar).toString();
    }
}
