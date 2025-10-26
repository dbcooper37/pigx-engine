package com.pigx.engine.data.hibernate.tenant;

import com.pigx.engine.core.foundation.context.TenantContextHolder;
import java.util.Map;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

@Component
/* loaded from: data-module-hibernate-3.5.7.0.jar:cn/herodotus/engine/data/hibernate/tenant/HerodotusTenantIdentifierResolver.class */
public class HerodotusTenantIdentifierResolver implements CurrentTenantIdentifierResolver<String>, HibernatePropertiesCustomizer {
    private static final Logger log = LoggerFactory.getLogger(HerodotusTenantIdentifierResolver.class);

    /* renamed from: resolveCurrentTenantIdentifier, reason: merged with bridge method [inline-methods] */
    public String m109resolveCurrentTenantIdentifier() {
        String currentTenantId = TenantContextHolder.getTenantId();
        log.trace("[Herodotus] |- Resolve Current Tenant Identifier is : [{}]", currentTenantId);
        return currentTenantId;
    }

    public boolean validateExistingCurrentSessions() {
        return true;
    }

    public void customize(Map<String, Object> hibernateProperties) {
        log.debug("[Herodotus] |- Apply hibernate properties [MULTI_TENANT_IDENTIFIER_RESOLVER]");
        hibernateProperties.put("hibernate.tenant_identifier_resolver", this);
    }
}
