package com.pigx.engine.oauth2.authentication.customizer;

import com.pigx.engine.core.identity.domain.AccessPrincipal;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.core.identity.service.EnhanceUserDetailsService;
import com.pigx.engine.core.identity.strategy.StrategyUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class HerodotusUserDetailsService implements EnhanceUserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(HerodotusUserDetailsService.class);

    private final StrategyUserDetailsService strategyUserDetailsService;

    public HerodotusUserDetailsService(StrategyUserDetailsService strategyUserDetailsService) {
        this.strategyUserDetailsService = strategyUserDetailsService;
    }


//    @Override
//    public HerodotusUser loadUserByUsername(String username) throws UsernameNotFoundException {
//        HerodotusUser HerodotusUser = strategyUserDetailsService.findUserDetailsByUsername(username);
//        log.debug("[PIGXD] |- UserDetailsService loaded user : [{}]", username);
//        return HerodotusUser;
//    }

    @Override
    public UserDetails loadUserBySocial(String source, AccessPrincipal accessPrincipal) throws UsernameNotFoundException {
        HerodotusUser HerodotusUser = strategyUserDetailsService.findUserDetailsBySocial(StringUtils.toRootUpperCase(source), accessPrincipal);
        log.debug("[PIGXD] |- UserDetailsService loaded social user : [{}]", HerodotusUser.getUsername());
        return HerodotusUser;
    }

    @Override
    public HerodotusUser loadHerodotusUserByUsername(String username) throws UsernameNotFoundException {
        HerodotusUser HerodotusUser = strategyUserDetailsService.findUserDetailsByUsername(username);
        log.debug("[PIGXD] |- UserDetailsService loaded user : [{}]", username);
        return HerodotusUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadHerodotusUserByUsername(username);
    }
}
