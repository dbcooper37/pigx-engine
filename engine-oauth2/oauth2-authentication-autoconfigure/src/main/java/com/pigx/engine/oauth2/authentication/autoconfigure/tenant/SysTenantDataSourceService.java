package com.pigx.engine.oauth2.authentication.autoconfigure.tenant;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.data.tenant.entity.SysTenantDataSource;
import com.pigx.engine.data.tenant.repository.SysTenantDataSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/* JADX WARN: Classes with same name are omitted:
  
 */
@Service
/* loaded from: oauth2-authentication-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/autoconfigure/tenant/SysTenantDataSourceService.class */
public class SysTenantDataSourceService extends AbstractJpaService<SysTenantDataSource, String> {
    private static final Logger log = LoggerFactory.getLogger(SysTenantDataSourceService.class);
    private final SysTenantDataSourceRepository sysTenantDataSourceRepository;

    public SysTenantDataSourceService(SysTenantDataSourceRepository sysTenantDataSourceRepository) {
        this.sysTenantDataSourceRepository = sysTenantDataSourceRepository;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysTenantDataSource, String> getRepository() {
        return this.sysTenantDataSourceRepository;
    }

    public SysTenantDataSource findByTenantId(String tenantId) {
        SysTenantDataSource sysRole = this.sysTenantDataSourceRepository.findByTenantId(tenantId);
        log.debug("[Herodotus] |- SysTenantDataSource Service findByTenantId.");
        return sysRole;
    }
}
