package com.pigx.engine.core.autoconfigure.oauth2.definition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;


public abstract class AbstractAuditorAware {

    private static final Logger log = LoggerFactory.getLogger(AbstractAuditorAware.class);

    protected String getName(OAuth2IntrospectionAuthenticatedPrincipal principal) {
        String username = principal.getName();
        log.trace("[PIGXD] |- Current auditor is : [{}]", username);
        return username;
    }
}
