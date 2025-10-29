package com.pigx.engine.core.identity.strategy;

import com.pigx.engine.core.identity.domain.AccessPrincipal;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import org.springframework.security.core.AuthenticationException;


public interface StrategyUserDetailsService {

    HerodotusUser findUserDetailsByUsername(String username) throws AuthenticationException;

    HerodotusUser findUserDetailsBySocial(String source, AccessPrincipal accessPrincipal) throws AuthenticationException;
}
