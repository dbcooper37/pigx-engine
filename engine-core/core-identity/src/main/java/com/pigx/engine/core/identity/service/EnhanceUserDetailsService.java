package com.pigx.engine.core.identity.service;

import com.pigx.engine.core.identity.domain.AccessPrincipal;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/service/EnhanceUserDetailsService.class */
public interface EnhanceUserDetailsService extends UserDetailsService {
    UserDetails loadUserBySocial(String source, AccessPrincipal accessPrincipal) throws AuthenticationException;

    HerodotusUser loadHerodotusUserByUsername(String username) throws UsernameNotFoundException;
}
