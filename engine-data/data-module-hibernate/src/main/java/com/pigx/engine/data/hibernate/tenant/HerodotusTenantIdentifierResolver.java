package com.pigx.engine.data.hibernate.tenant;

import com.pigx.engine.core.foundation.context.TenantContextHolder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class HerodotusTenantIdentifierResolver implements CurrentTenantIdentifierResolver<String>, HibernatePropertiesCustomizer {

    private static final Logger log = LoggerFactory.getLogger(HerodotusTenantIdentifierResolver.class);

    @Override
    public String resolveCurrentTenantIdentifier() {
        String currentTenantId = TenantContextHolder.getTenantId();
        log.trace("[PIGXD] |- Resolve Current Tenant Identifier is : [{}]", currentTenantId);
        return currentTenantId;
    }

    /**
     * Additionally, if the CurrentTenantIdentifierResolver implementation returns true for its validateExistingCurrentSessions method,
     * Hibernate will make sure any existing sessions that are found in scope have a matching tenant identifier.
     * This capability is only pertinent when the CurrentTenantIdentifierResolver is used in current-session settings.
     *
     * @return 确保已经存在的 Session 都有一个对应的 Tenant ID
     */
    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        log.debug("[PIGXD] |- Apply hibernate properties [MULTI_TENANT_IDENTIFIER_RESOLVER]");
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }
}
