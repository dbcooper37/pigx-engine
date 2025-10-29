package com.pigx.engine.data.tenant.constant;

import com.pigx.engine.core.definition.constant.BaseConstants;


public interface TenantConstants extends BaseConstants {

    String PROPERTY_PREFIX_MULTI_TENANT = PROPERTY_PREFIX_DATA + ".multi-tenant";
    String ITEM_MULTI_TENANT_APPROACH = PROPERTY_PREFIX_MULTI_TENANT + ".approach";

    String CORE_AREA_PREFIX = AREA_PREFIX + "core:";
    String REGION_SYS_TENANT_DATASOURCE = CORE_AREA_PREFIX + "sys:tenant:datasource";
}
