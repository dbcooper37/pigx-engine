package com.pigx.engine.core.identity.oauth2;

import com.pigx.engine.core.identity.domain.UserPrincipal;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/oauth2/BearerTokenResolver.class */
public interface BearerTokenResolver {
    UserPrincipal resolve(String token);
}
