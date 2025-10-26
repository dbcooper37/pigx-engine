package com.pigx.engine.core.identity.service;

import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import java.util.Set;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/service/ClientDetailsService.class */
public interface ClientDetailsService {
    Set<HerodotusGrantedAuthority> findAuthoritiesById(String clientId);
}
