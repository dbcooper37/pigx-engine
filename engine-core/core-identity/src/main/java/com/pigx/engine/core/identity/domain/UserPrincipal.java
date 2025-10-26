package com.pigx.engine.core.identity.domain;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/domain/UserPrincipal.class */
public class UserPrincipal implements HerodotusPrincipal, Serializable {
    private String id;
    private String name;
    private String email;
    private String avatar;
    private Set<String> roles;
    private String employeeId;

    public UserPrincipal() {
    }

    public UserPrincipal(String id, String name, String email, String avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }

    @Override // com.pigx.engine.core.identity.domain.HerodotusPrincipal
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override // java.security.Principal
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override // com.pigx.engine.core.identity.domain.HerodotusPrincipal
    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override // com.pigx.engine.core.identity.domain.HerodotusPrincipal
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(SystemConstants.SCOPE_OPENID, this.id);
        map.put(SystemConstants.SCOPE_EMAIL, this.email);
        map.put(SystemConstants.USERNAME, this.name);
        map.put(SystemConstants.ROLES, this.roles);
        map.put(SystemConstants.EMPLOYEE_ID, this.employeeId);
        map.put(SystemConstants.AVATAR, this.avatar);
        return map;
    }

    @Override // java.security.Principal
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name);
    }

    @Override // java.security.Principal
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override // java.security.Principal
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", this.id).add("name", this.name).add(SystemConstants.SCOPE_EMAIL, this.email).add(SystemConstants.AVATAR, this.avatar).add(SystemConstants.ROLES, this.roles).add(SystemConstants.EMPLOYEE_ID, this.employeeId).toString();
    }
}
