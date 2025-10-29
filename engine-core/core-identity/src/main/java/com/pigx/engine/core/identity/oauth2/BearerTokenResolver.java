package com.pigx.engine.core.identity.oauth2;

import com.pigx.engine.core.identity.domain.UserPrincipal;


public interface BearerTokenResolver {

    UserPrincipal resolve(String token);
}
