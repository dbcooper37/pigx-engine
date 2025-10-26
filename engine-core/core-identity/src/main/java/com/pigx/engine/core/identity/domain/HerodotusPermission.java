package com.pigx.engine.core.identity.domain;

import com.google.common.base.MoreObjects;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/domain/HerodotusPermission.class */
public class HerodotusPermission {
    private String permissionId;
    private String permissionCode;
    private String permissionName;

    public String getPermissionId() {
        return this.permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionCode() {
        return this.permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return this.permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("permissionId", this.permissionId).add("permissionCode", this.permissionCode).add("permissionName", this.permissionName).toString();
    }
}
