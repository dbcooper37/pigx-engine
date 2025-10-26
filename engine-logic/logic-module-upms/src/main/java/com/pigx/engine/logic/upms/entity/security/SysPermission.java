package com.pigx.engine.logic.upms.entity.security;

import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_PERMISSION)
@Schema(name = "系统权限")
@Cacheable
@Entity
@Table(name = "sys_permission", indexes = {@Index(name = "sys_permission_id_idx", columnList = "permission_id")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/security/SysPermission.class */
public class SysPermission extends AbstractSysEntity {

    @Id
    @Schema(name = "权限ID")
    @UuidGenerator
    @Column(name = "permission_id", length = 64)
    private String permissionId;

    @Column(name = "permission_code", length = 128)
    @Schema(name = "权限代码")
    private String permissionCode;

    @Column(name = "permission_name", length = 1024)
    @Schema(name = "权限名称")
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysPermission that = (SysPermission) o;
        return Objects.equal(this.permissionId, that.permissionId);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.permissionId});
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("permissionId", this.permissionId).add("permissionCode", this.permissionCode).add("permissionName", this.permissionName).toString();
    }
}
