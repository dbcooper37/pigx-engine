package com.pigx.engine.logic.identity.entity;

import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.identity.generator.OAuth2PermissionIdGenerator;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import com.google.common.base.MoreObjects;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_PERMISSION)
@Cacheable
@Entity
@Table(name = "oauth2_permission", indexes = {@Index(name = "oauth2_permission_id_idx", columnList = "permission_id")})
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/entity/OAuth2Permission.class */
public class OAuth2Permission extends AbstractSysEntity {

    @Id
    @Column(name = "permission_id", length = 64)
    @OAuth2PermissionIdGenerator
    private String permissionId;

    @Column(name = "permission_code", length = 128)
    private String permissionCode;

    @Column(name = "permission_name", length = 128)
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

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("permissionId", this.permissionId).add("permissionCode", this.permissionCode).add("permissionName", this.permissionName).toString();
    }
}
