package com.pigx.engine.oauth2.extension.listener;

import com.pigx.engine.oauth2.extension.manager.OAuth2ComplianceManager;
import com.pigx.engine.oauth2.persistence.sas.jpa.event.SignOutComplianceEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;


public class SignOutComplianceListener implements ApplicationListener<SignOutComplianceEvent> {

    private final OAuth2ComplianceManager complianceManager;

    public SignOutComplianceListener(OAuth2ComplianceManager complianceManager) {
        this.complianceManager = complianceManager;
    }

    @Override
    public void onApplicationEvent(SignOutComplianceEvent event) {
        OAuth2Authorization authorization = event.getData();
        HttpServletRequest request = event.getRequest();
        complianceManager.signOut(authorization, request);
    }
}
