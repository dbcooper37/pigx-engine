package com.pigx.engine.web.servlet.secure;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/secure/XssHttpServletFilter.class */
public class XssHttpServletFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(XssHttpServletFilter.class);

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request);
        log.trace("[Herodotus] |- XssHttpServletFilter wrapper request for [{}].", request.getRequestURI());
        filterChain.doFilter(xssRequest, servletResponse);
    }
}
