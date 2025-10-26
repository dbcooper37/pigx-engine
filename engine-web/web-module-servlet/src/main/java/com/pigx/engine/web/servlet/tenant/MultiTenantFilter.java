package com.pigx.engine.web.servlet.tenant;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.foundation.context.TenantContextHolder;
import com.pigx.engine.web.core.servlet.utils.HeaderUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/tenant/MultiTenantFilter.class */
public class MultiTenantFilter extends GenericFilterBean {
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String tenantId = HeaderUtils.getHerodotusTenantId(request);
        TenantContextHolder.setTenantId(StringUtils.isBlank(tenantId) ? SystemConstants.TENANT_ID : tenantId);
        filterChain.doFilter(servletRequest, servletResponse);
        TenantContextHolder.clear();
    }

    public void destroy() {
        TenantContextHolder.clear();
    }
}
