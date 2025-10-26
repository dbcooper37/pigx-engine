package com.pigx.engine.oauth2.extension.listener;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.oauth2.extension.manager.OAuth2ComplianceManager;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/listener/AuthenticationFailureListener.class */
public class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFailureListener.class);
    private final OAuth2ComplianceManager complianceManager;

    public AuthenticationFailureListener(OAuth2ComplianceManager complianceManager) {
        this.complianceManager = complianceManager;
    }

    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) throws UsernameNotFoundException {
        log.debug("[Herodotus] |- User sign in catch failure event : [{}].", event.getClass().getName());
        if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            OAuth2AuthorizationGrantAuthenticationToken authentication = event.getAuthentication();
            String username = null;
            if (authentication instanceof OAuth2AuthorizationGrantAuthenticationToken) {
                OAuth2AuthorizationGrantAuthenticationToken token = authentication;
                log.debug("[Herodotus] |- Toke object in failure event  is OAuth2AuthorizationGrantAuthenticationToken");
                Map<String, Object> params = token.getAdditionalParameters();
                username = getPrincipal(params);
            }
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken token2 = (UsernamePasswordAuthenticationToken) authentication;
                log.debug("[Herodotus] |- Toke object in failure event  is UsernamePasswordAuthenticationToken");
                Object principal = token2.getPrincipal();
                if (principal instanceof String) {
                    username = (String) principal;
                }
            }
            if (StringUtils.isNotBlank(username)) {
                this.complianceManager.countingSignInFailureTimes(username);
            }
        }
    }

    private String getPrincipal(Map<String, Object> params) {
        if (MapUtils.isNotEmpty(params) && params.containsKey(SystemConstants.USERNAME)) {
            Object value = params.get(SystemConstants.USERNAME);
            if (ObjectUtils.isNotEmpty(value)) {
                return (String) value;
            }
            return null;
        }
        return null;
    }
}
