package com.pigx.engine.core.foundation.context;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.apache.commons.lang3.StringUtils;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/context/TenantContextHolder.class */
public class TenantContextHolder {
    private static final ThreadLocal<String> CURRENT_CONTEXT = new TransmittableThreadLocal();

    public static String getTenantId() {
        String tenantId = CURRENT_CONTEXT.get();
        if (StringUtils.isBlank(tenantId)) {
            tenantId = SystemConstants.TENANT_ID;
        }
        return tenantId;
    }

    public static void setTenantId(final String tenantId) {
        if (StringUtils.isBlank(tenantId)) {
            CURRENT_CONTEXT.set(SystemConstants.TENANT_ID);
        } else {
            CURRENT_CONTEXT.set(tenantId);
        }
    }

    public static void clear() {
        CURRENT_CONTEXT.remove();
    }
}
