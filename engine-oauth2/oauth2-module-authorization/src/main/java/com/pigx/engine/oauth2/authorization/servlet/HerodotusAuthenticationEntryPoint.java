package com.pigx.engine.oauth2.authorization.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.definition.SecurityGlobalExceptionHandler;
import com.pigx.engine.web.core.servlet.template.AbstractResponseHandler;
import com.pigx.engine.web.core.servlet.template.ThymeleafTemplateHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;


public class HerodotusAuthenticationEntryPoint extends AbstractResponseHandler implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(HerodotusAuthenticationEntryPoint.class);

    public HerodotusAuthenticationEntryPoint(ThymeleafTemplateHandler templateHandler) {
        super(templateHandler);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.warn("[PIGXD] |- Not authenticated for request [{}],which in servlet service!", request.getRequestURI());

        process(request, response, () -> SecurityGlobalExceptionHandler.resolveSecurityException(exception, request.getRequestURI()));
    }
}
