package com.pigx.engine.logic.upms.entity.security;

import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.google.common.base.MoreObjects;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_ROLE)
@Cacheable
@Entity
@Table(name = "sys_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"role_code"})}, indexes = {@Index(name = "sys_role_rid_idx", columnList = "role_id"), @Index(name = "sys_role_rcd_idx", columnList = "role_code")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/security/SysRole.class */
public class SysRole extends AbstractSysEntity {

    @Id
    @Column(name = "role_id", length = 64)
    @UuidGenerator
    private String roleId;

    @Column(name = "role_code", length = 128, unique = true)
    private String roleCode;

    @Column(name = "role_name", length = 128)
    private String roleName;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_PERMISSION)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "permission_id"})}, indexes = {@Index(name = "sys_role_permission_rid_idx", columnList = "role_id"), @Index(name = "sys_role_permission_pid_idx", columnList = "permission_id")})
    @Fetch(FetchMode.SUBSELECT)
    private Set<SysPermission> permissions = new HashSet();

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<SysPermission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<SysPermission> permissions) {
        this.permissions = permissions;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRole sysRole = (SysRole) o;
        return new EqualsBuilder().append(getRoleId(), sysRole.getRoleId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getRoleId()).toHashCode();
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("roleId", this.roleId).add("roleCode", this.roleCode).add("roleName", this.roleName).toString();
    }
}
