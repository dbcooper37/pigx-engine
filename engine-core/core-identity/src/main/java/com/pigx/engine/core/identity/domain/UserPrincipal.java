package com.pigx.engine.core.identity.domain;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.definition.constant.SystemConstants;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


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

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getEmployeeId() {
        return employeeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("email", email)
                .add("avatar", avatar)
                .add("roles", roles)
                .add("employeeId", employeeId)
                .toString();
    }
}
