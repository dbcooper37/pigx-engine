package com.pigx.engine.oauth2.authentication.autoconfigure.tenant;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.data.tenant.entity.SysTenantDataSource;
import com.pigx.engine.data.tenant.repository.SysTenantDataSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class SysTenantDataSourceService extends AbstractJpaService<SysTenantDataSource, String> {

    private static final Logger log = LoggerFactory.getLogger(SysTenantDataSourceService.class);

    private final SysTenantDataSourceRepository sysTenantDataSourceRepository;

    public SysTenantDataSourceService(SysTenantDataSourceRepository sysTenantDataSourceRepository) {
        this.sysTenantDataSourceRepository = sysTenantDataSourceRepository;
    }

    @Override
    public BaseJpaRepository<SysTenantDataSource, String> getRepository() {
        return sysTenantDataSourceRepository;
    }

    public SysTenantDataSource findByTenantId(String tenantId) {
        SysTenantDataSource sysRole = sysTenantDataSourceRepository.findByTenantId(tenantId);
        log.debug("[PIGXD] |- SysTenantDataSource Service findByTenantId.");
        return sysRole;
    }
}
