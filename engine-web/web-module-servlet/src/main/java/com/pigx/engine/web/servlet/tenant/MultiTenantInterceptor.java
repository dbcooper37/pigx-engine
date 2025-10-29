package com.pigx.engine.web.servlet.tenant;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.foundation.context.TenantContextHolder;
import com.pigx.engine.web.core.servlet.utils.HeaderUtils;
import com.pigx.engine.web.core.servlet.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;


public class MultiTenantInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(MultiTenantInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String tenantId = HeaderUtils.getHerodotusTenantId(request);
        if (StringUtils.isBlank(tenantId)) {
            tenantId = SystemConstants.TENANT_ID;
        }
        TenantContextHolder.setTenantId(tenantId);
        log.debug("[PIGXD] |- TENANT ID is : [{}].", tenantId);

        String path = request.getRequestURI();
        String sessionId = SessionUtils.getSessionId(request);
        String herodotusSessionId = HeaderUtils.getHerodotusSessionId(request);

        log.debug("[PIGXD] |- SESSION ID for [{}] is : [{}].", path, sessionId);
        log.debug("[PIGXD] |- SESSION ID of HERODOTUS for [{}] is : [{}].", path, herodotusSessionId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String path = request.getRequestURI();
        TenantContextHolder.clear();
        log.debug("[PIGXD] |- Tenant Interceptor CLEAR tenantId for request [{}].", path);
    }
}
