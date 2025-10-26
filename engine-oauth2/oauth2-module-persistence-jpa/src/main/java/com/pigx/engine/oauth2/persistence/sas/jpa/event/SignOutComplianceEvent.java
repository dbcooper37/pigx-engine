package com.pigx.engine.oauth2.persistence.sas.jpa.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/event/SignOutComplianceEvent.class */
public class SignOutComplianceEvent extends AbstractApplicationEvent<OAuth2Authorization> {
    private final HttpServletRequest request;

    public SignOutComplianceEvent(OAuth2Authorization authorization, HttpServletRequest request) {
        super(authorization);
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }
}
