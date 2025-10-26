package com.pigx.engine.autoconfigure.oauth2.definition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/definition/AbstractAuditorAware.class */
public abstract class AbstractAuditorAware {
    private static final Logger log = LoggerFactory.getLogger(AbstractAuditorAware.class);

    protected String getName(OAuth2IntrospectionAuthenticatedPrincipal principal) {
        String username = principal.getName();
        log.trace("[Herodotus] |- Current auditor is : [{}]", username);
        return username;
    }
}
