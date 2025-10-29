package com.pigx.engine.oauth2.persistence.sas.jpa.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;


public class SignOutComplianceEvent extends AbstractApplicationEvent<OAuth2Authorization> {

    private final HttpServletRequest request;

    public SignOutComplianceEvent(OAuth2Authorization authorization, HttpServletRequest request) {
        super(authorization);
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
