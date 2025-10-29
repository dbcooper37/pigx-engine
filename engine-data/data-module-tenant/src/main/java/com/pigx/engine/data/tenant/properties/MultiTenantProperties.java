package com.pigx.engine.data.tenant.properties;

import com.pigx.engine.data.tenant.constant.TenantConstants;
import com.pigx.engine.data.tenant.enums.MultiTenant;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = TenantConstants.PROPERTY_PREFIX_MULTI_TENANT)
public class MultiTenantProperties {

    /**
     * 多租户数据源扫描包
     */
    private String[] packageToScan = new String[]{"com.pigx.engine", "com.pigx.professional"};

    /**
     * 多租户模式，默认：discriminator
     */
    private MultiTenant approach = MultiTenant.DISCRIMINATOR;

    public String[] getPackageToScan() {
        return packageToScan;
    }

    public void setPackageToScan(String[] packageToScan) {
        this.packageToScan = packageToScan;
    }

    public MultiTenant getApproach() {
        return approach;
    }

    public void setApproach(MultiTenant approach) {
        this.approach = approach;
    }
}
