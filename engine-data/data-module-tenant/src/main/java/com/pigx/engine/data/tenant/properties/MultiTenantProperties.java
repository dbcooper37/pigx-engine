package com.pigx.engine.data.tenant.properties;

import com.pigx.engine.data.tenant.constant.TenantConstants;
import com.pigx.engine.data.tenant.enums.MultiTenant;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = TenantConstants.PROPERTY_PREFIX_MULTI_TENANT)
/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/properties/MultiTenantProperties.class */
public class MultiTenantProperties {
    private String[] packageToScan = {"com.pigx.engine", "com.pigx.professional"};
    private MultiTenant approach = MultiTenant.DISCRIMINATOR;

    public String[] getPackageToScan() {
        return this.packageToScan;
    }

    public void setPackageToScan(String[] packageToScan) {
        this.packageToScan = packageToScan;
    }

    public MultiTenant getApproach() {
        return this.approach;
    }

    public void setApproach(MultiTenant approach) {
        this.approach = approach;
    }
}
