package com.pigx.engine.data.tenant.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.tenant.entity.SysTenantDataSource;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;


public interface SysTenantDataSourceRepository extends BaseJpaRepository<SysTenantDataSource, String> {

    /**
     * 根据租户ID查询数据源
     *
     * @param tenantId 租户ID
     * @return {@link SysTenantDataSource}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysTenantDataSource findByTenantId(String tenantId);
}
