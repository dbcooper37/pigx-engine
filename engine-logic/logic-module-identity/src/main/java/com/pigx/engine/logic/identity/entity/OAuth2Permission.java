package com.pigx.engine.logic.identity.entity;

import com.google.common.base.MoreObjects;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.identity.generator.OAuth2PermissionIdGenerator;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "oauth2_permission", indexes = {@Index(name = "oauth2_permission_id_idx", columnList = "permission_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_PERMISSION)
public class OAuth2Permission extends AbstractSysEntity {

    @Id
    @OAuth2PermissionIdGenerator
    @Column(name = "permission_id", length = 64)
    private String permissionId;

    @Column(name = "permission_code", length = 128)
    private String permissionCode;

    @Column(name = "permission_name", length = 128)
    private String permissionName;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("permissionId", permissionId)
                .add("permissionCode", permissionCode)
                .add("permissionName", permissionName)
                .toString();
    }
}
