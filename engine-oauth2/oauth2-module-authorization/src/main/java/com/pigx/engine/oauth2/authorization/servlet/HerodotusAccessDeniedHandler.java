package com.pigx.engine.oauth2.authorization.servlet;

import com.pigx.engine.core.autoconfigure.oauth2.definition.SecurityGlobalExceptionHandler;
import com.pigx.engine.web.core.servlet.template.AbstractResponseHandler;
import com.pigx.engine.web.core.servlet.template.ThymeleafTemplateHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;


public class HerodotusAccessDeniedHandler extends AbstractResponseHandler implements AccessDeniedHandler {

    private static final Logger log = LoggerFactory.getLogger(HerodotusAccessDeniedHandler.class);

    public HerodotusAccessDeniedHandler(ThymeleafTemplateHandler templateHandler) {
        super(templateHandler);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {

        log.warn("[PIGXD] |- Access Denied for request [{}],which in reactive service!", request.getRequestURI());

        process(request, response, () -> SecurityGlobalExceptionHandler.resolveSecurityException(exception, request.getRequestURI()));
    }
}
