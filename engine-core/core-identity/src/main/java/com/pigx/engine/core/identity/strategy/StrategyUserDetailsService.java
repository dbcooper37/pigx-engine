package com.pigx.engine.core.identity.strategy;

import com.pigx.engine.core.identity.domain.AccessPrincipal;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import org.springframework.security.core.AuthenticationException;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/strategy/StrategyUserDetailsService.class */
public interface StrategyUserDetailsService {
    HerodotusUser findUserDetailsByUsername(String username) throws AuthenticationException;

    HerodotusUser findUserDetailsBySocial(String source, AccessPrincipal accessPrincipal) throws AuthenticationException;
}
