package com.pigx.engine.logic.upms.entity.security;

import com.pigx.engine.core.identity.enums.AccountCategory;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_DEFAULT_ROLE)
@Cacheable
@Entity
@Table(name = "sys_default_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"default_id", "scene"})}, indexes = {@Index(name = "sys_default_role_did_idx", columnList = "default_id"), @Index(name = "sys_default_role_rid_idx", columnList = "role_id")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/security/SysDefaultRole.class */
public class SysDefaultRole extends AbstractSysEntity {

    @Id
    @Column(name = "default_id", length = 64)
    @UuidGenerator
    private String defaultId;

    @Column(name = "scene", unique = true)
    @Enumerated(EnumType.STRING)
    @Schema(name = "场景")
    private AccountCategory scene = AccountCategory.INSTITUTION;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_ROLE)
    @ManyToOne
    @Schema(name = "角色ID")
    @JoinColumn(name = "role_id", nullable = false)
    private SysRole role;

    public String getDefaultId() {
        return this.defaultId;
    }

    public void setDefaultId(String defaultId) {
        this.defaultId = defaultId;
    }

    public AccountCategory getScene() {
        return this.scene;
    }

    public void setScene(AccountCategory scene) {
        this.scene = scene;
    }

    public SysRole getRole() {
        return this.role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("defaultId", this.defaultId).add("supplierType", this.scene).add("role", this.role).toString();
    }
}
