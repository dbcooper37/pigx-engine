package com.pigx.engine.logic.identity.definition;

import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.service.EnhanceClientDetailsService;
import com.pigx.engine.logic.identity.entity.OAuth2Application;
import com.pigx.engine.logic.identity.entity.OAuth2Permission;
import com.pigx.engine.logic.identity.entity.OAuth2Scope;
import com.pigx.engine.logic.identity.service.OAuth2ApplicationService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/definition/HerodotusClientDetailsService.class */
public class HerodotusClientDetailsService implements EnhanceClientDetailsService {
    private final OAuth2ApplicationService applicationService;

    public HerodotusClientDetailsService(OAuth2ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override // com.pigx.engine.core.identity.service.ClientDetailsService
    public Set<HerodotusGrantedAuthority> findAuthoritiesById(String clientId) {
        OAuth2Application application = this.applicationService.findByClientId(clientId);
        if (ObjectUtils.isNotEmpty(application)) {
            Set<OAuth2Scope> scopes = application.getScopes();
            Set<HerodotusGrantedAuthority> result = new HashSet<>();
            if (CollectionUtils.isNotEmpty(scopes)) {
                for (OAuth2Scope scope : scopes) {
                    Set<OAuth2Permission> permissions = scope.getPermissions();
                    if (CollectionUtils.isNotEmpty(permissions)) {
                        Set<HerodotusGrantedAuthority> grantedAuthorities = (Set) permissions.stream().map(item -> {
                            return new HerodotusGrantedAuthority(item.getPermissionCode());
                        }).collect(Collectors.toSet());
                        result.addAll(grantedAuthorities);
                    }
                }
            }
            return result;
        }
        return new HashSet();
    }
}
