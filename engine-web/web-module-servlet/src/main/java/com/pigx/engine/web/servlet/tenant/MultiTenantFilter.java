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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


/**
 * Filter that sets up tenant context for each request.
 * <p>
 * This filter extracts the tenant ID from the request header and sets it in
 * {@link TenantContextHolder} for the duration of the request.
 * </p>
 *
 * <p><b>Thread Safety:</b> Uses try-finally to ensure cleanup even on exceptions.</p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public class MultiTenantFilter extends GenericFilterBean {

    private static final Logger log = LoggerFactory.getLogger(MultiTenantFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String tenantId = HeaderUtils.getHerodotusTenantId(request);
        TenantContextHolder.setTenantId(StringUtils.isBlank(tenantId) ? SystemConstants.TENANT_ID : tenantId);
        
        log.debug("[PIGXD] |- Tenant filter set tenant ID: [{}] for request [{}]", 
                TenantContextHolder.getTenantId(), request.getRequestURI());

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // Always clear tenant context to prevent memory leaks in thread pools
            TenantContextHolder.clear();
            log.trace("[PIGXD] |- Tenant filter cleared tenant context for request [{}]", 
                    request.getRequestURI());
        }
    }

    @Override
    public void destroy() {
        TenantContextHolder.clear();
    }
}
