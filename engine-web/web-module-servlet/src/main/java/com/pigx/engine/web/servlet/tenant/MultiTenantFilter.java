package com.pigx.engine.web.servlet.tenant;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.foundation.context.TenantContextHolder;
import com.pigx.engine.web.core.servlet.utils.HeaderUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


public class MultiTenantFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String tenantId = HeaderUtils.getHerodotusTenantId(request);
        TenantContextHolder.setTenantId(StringUtils.isBlank(tenantId) ? SystemConstants.TENANT_ID : tenantId);

        filterChain.doFilter(servletRequest, servletResponse);
        TenantContextHolder.clear();
    }

    @Override
    public void destroy() {
        TenantContextHolder.clear();
    }
}
