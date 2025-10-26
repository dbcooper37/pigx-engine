package com.pigx.engine.oauth2.authentication.customizer;

import com.pigx.engine.core.identity.domain.AccessPrincipal;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.core.identity.service.EnhanceUserDetailsService;
import com.pigx.engine.core.identity.strategy.StrategyUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/* loaded from: oauth2-module-authentication-3.5.7.0.jar:cn/herodotus/engine/oauth2/authentication/customizer/HerodotusUserDetailsService.class */
public class HerodotusUserDetailsService implements EnhanceUserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(HerodotusUserDetailsService.class);
    private final StrategyUserDetailsService strategyUserDetailsService;

    public HerodotusUserDetailsService(StrategyUserDetailsService strategyUserDetailsService) {
        this.strategyUserDetailsService = strategyUserDetailsService;
    }

    @Override // com.pigx.engine.core.identity.service.EnhanceUserDetailsService
    public UserDetails loadUserBySocial(String source, AccessPrincipal accessPrincipal) throws UsernameNotFoundException, AuthenticationException {
        HerodotusUser HerodotusUser = this.strategyUserDetailsService.findUserDetailsBySocial(StringUtils.toRootUpperCase(source), accessPrincipal);
        log.debug("[Herodotus] |- UserDetailsService loaded social user : [{}]", HerodotusUser.getUsername());
        return HerodotusUser;
    }

    @Override // com.pigx.engine.core.identity.service.EnhanceUserDetailsService
    public HerodotusUser loadHerodotusUserByUsername(String username) throws UsernameNotFoundException, AuthenticationException {
        HerodotusUser HerodotusUser = this.strategyUserDetailsService.findUserDetailsByUsername(username);
        log.debug("[Herodotus] |- UserDetailsService loaded user : [{}]", username);
        return HerodotusUser;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadHerodotusUserByUsername(username);
    }
}
