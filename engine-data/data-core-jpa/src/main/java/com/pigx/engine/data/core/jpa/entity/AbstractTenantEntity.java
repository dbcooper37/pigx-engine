package com.pigx.engine.data.core.jpa.entity;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.TenantId;


@MappedSuperclass
public abstract class AbstractTenantEntity implements BaseEntity {

    @Schema(name = "租户ID", description = "Partitioned 类型租户ID")
    @Column(name = "tenant_id", length = 20)
    @TenantId
    private String tenantId = SystemConstants.TENANT_ID;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
