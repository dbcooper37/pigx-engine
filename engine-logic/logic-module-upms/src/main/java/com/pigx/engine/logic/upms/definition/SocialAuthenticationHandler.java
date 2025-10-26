package com.pigx.engine.logic.upms.definition;

import com.pigx.engine.core.identity.domain.AccessPrincipal;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import org.springframework.security.core.AuthenticationException;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/definition/SocialAuthenticationHandler.class */
public interface SocialAuthenticationHandler {
    HerodotusUser authentication(String source, AccessPrincipal accessPrincipal) throws AuthenticationException;
}
