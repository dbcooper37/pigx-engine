package com.pigx.engine.data.core.jpa.entity;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.TenantId;

@MappedSuperclass
/* loaded from: data-core-jpa-3.5.7.0.jar:cn/herodotus/engine/data/core/jpa/entity/AbstractTenantEntity.class */
public abstract class AbstractTenantEntity implements BaseEntity {

    @Column(name = "tenant_id", length = ErrorCodeMapperBuilderOrdered.CAPTCHA)
    @Schema(name = "租户ID", description = "Partitioned 类型租户ID")
    @TenantId
    private String tenantId = SystemConstants.TENANT_ID;

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
