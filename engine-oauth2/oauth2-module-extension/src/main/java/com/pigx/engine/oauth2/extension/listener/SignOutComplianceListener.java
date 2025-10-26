package com.pigx.engine.oauth2.extension.listener;

import com.pigx.engine.oauth2.extension.manager.OAuth2ComplianceManager;
import com.pigx.engine.oauth2.persistence.sas.jpa.event.SignOutComplianceEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

/* loaded from: oauth2-module-extension-3.5.7.0.jar:cn/herodotus/engine/oauth2/extension/listener/SignOutComplianceListener.class */
public class SignOutComplianceListener implements ApplicationListener<SignOutComplianceEvent> {
    private final OAuth2ComplianceManager complianceManager;

    public SignOutComplianceListener(OAuth2ComplianceManager complianceManager) {
        this.complianceManager = complianceManager;
    }

    public void onApplicationEvent(SignOutComplianceEvent event) throws UsernameNotFoundException {
        OAuth2Authorization authorization = event.getData();
        HttpServletRequest request = event.getRequest();
        this.complianceManager.signOut(authorization, request);
    }
}
