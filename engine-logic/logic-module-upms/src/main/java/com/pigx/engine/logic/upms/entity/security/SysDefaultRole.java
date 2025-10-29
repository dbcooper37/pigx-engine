package com.pigx.engine.logic.upms.entity.security;

import com.google.common.base.MoreObjects;
import com.pigx.engine.core.identity.enums.AccountCategory;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;


@Entity
@Table(name = "sys_default_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"default_id", "scene"})},
        indexes = {
                @Index(name = "sys_default_role_did_idx", columnList = "default_id"),
                @Index(name = "sys_default_role_rid_idx", columnList = "role_id")}
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_DEFAULT_ROLE)
public class SysDefaultRole extends AbstractSysEntity {

    @Id
    @UuidGenerator
    @Column(name = "default_id", length = 64)
    private String defaultId;

    @Schema(name = "场景")
    @Column(name = "scene", unique = true)
    @Enumerated(EnumType.STRING)
    private AccountCategory scene = AccountCategory.INSTITUTION;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_ROLE)
    @Schema(name = "角色ID")
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private SysRole role;

    public String getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(String defaultId) {
        this.defaultId = defaultId;
    }

    public AccountCategory getScene() {
        return scene;
    }

    public void setScene(AccountCategory scene) {
        this.scene = scene;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("defaultId", defaultId)
                .add("supplierType", scene)
                .add("role", role)
                .toString();
    }
}
