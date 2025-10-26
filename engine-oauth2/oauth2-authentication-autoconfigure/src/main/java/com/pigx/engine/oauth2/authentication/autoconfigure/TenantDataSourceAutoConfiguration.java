package com.pigx.engine.oauth2.authentication.autoconfigure;

import com.pigx.engine.data.tenant.condition.ConditionalOnMultiTenant;
import com.pigx.engine.data.tenant.enums.MultiTenant;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/* JADX WARN: Classes with same name are omitted:
  
 */
@AutoConfiguration
@ConditionalOnMultiTenant(MultiTenant.DATABASE)
@ComponentScan(basePackages = {"com.pigx.engine.oauth2.authentication.autoconfigure.tenant"})
/* loaded from: oauth2-authentication-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/autoconfigure/TenantDataSourceAutoConfiguration.class */
public class TenantDataSourceAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(TenantDataSourceAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Tenant Data Source] Configure.");
    }
}
