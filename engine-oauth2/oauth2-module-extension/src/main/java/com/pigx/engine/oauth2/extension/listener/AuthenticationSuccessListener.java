package com.pigx.engine.oauth2.extension.listener;

import com.pigx.engine.oauth2.extension.manager.OAuth2ComplianceManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/listener/AuthenticationSuccessListener.class */
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessListener.class);
    private final OAuth2ComplianceManager complianceManager;

    public AuthenticationSuccessListener(OAuth2ComplianceManager complianceManager) {
        this.complianceManager = complianceManager;
    }

    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        OAuth2AccessTokenAuthenticationToken authentication = event.getAuthentication();
        if (authentication instanceof OAuth2AccessTokenAuthenticationToken) {
            OAuth2AccessTokenAuthenticationToken authenticationToken = authentication;
            ServletRequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (ObjectUtils.isNotEmpty(requestAttributes) && (requestAttributes instanceof ServletRequestAttributes)) {
                ServletRequestAttributes servletRequestAttributes = requestAttributes;
                HttpServletRequest request = servletRequestAttributes.getRequest();
                if (ObjectUtils.isNotEmpty(request)) {
                    this.complianceManager.signIn(authenticationToken, request);
                    return;
                }
                return;
            }
            log.warn("[Herodotus] |- Can not get request and user name, skip!");
        }
    }
}
