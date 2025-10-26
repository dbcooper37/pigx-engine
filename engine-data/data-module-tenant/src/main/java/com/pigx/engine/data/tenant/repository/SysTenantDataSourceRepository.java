package com.pigx.engine.data.tenant.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.tenant.entity.SysTenantDataSource;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/repository/SysTenantDataSourceRepository.class */
public interface SysTenantDataSourceRepository extends BaseJpaRepository<SysTenantDataSource, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    SysTenantDataSource findByTenantId(String tenantId);
}
