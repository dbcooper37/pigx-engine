package com.pigx.engine.oauth2.authentication.autoconfigure;

import com.pigx.engine.data.tenant.condition.ConditionalOnMultiTenant;
import com.pigx.engine.data.tenant.enums.MultiTenant;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@AutoConfiguration
@ConditionalOnMultiTenant(MultiTenant.DATABASE)
@ComponentScan(basePackages = {
        "com.pigx.engine.oauth2.authentication.autoconfigure.tenant",
})
public class TenantDataSourceAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TenantDataSourceAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Tenant Data Source] Configure.");
    }
}
