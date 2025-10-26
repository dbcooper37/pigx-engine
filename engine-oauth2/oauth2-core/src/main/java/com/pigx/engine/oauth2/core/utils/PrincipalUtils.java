package com.pigx.engine.oauth2.core.utils;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

/* loaded from: oauth2-core-3.5.7.0.jar:cn/herodotus/engine/oauth2/core/utils/PrincipalUtils.class */
public class PrincipalUtils {
    public static UserPrincipal toPrincipalDetails(HerodotusUser herodotusUser) {
        UserPrincipal details = new UserPrincipal();
        details.setId(herodotusUser.getUserId());
        details.setName(herodotusUser.getUsername());
        details.setRoles(herodotusUser.getRoles());
        details.setAvatar(herodotusUser.getAvatar());
        details.setEmployeeId(herodotusUser.getEmployeeId());
        return details;
    }

    public static UserPrincipal toPrincipalDetails(OAuth2AuthenticatedPrincipal authenticatedPrincipal) {
        UserPrincipal details = new UserPrincipal();
        details.setId((String) authenticatedPrincipal.getAttribute(SystemConstants.SCOPE_OPENID));
        details.setName(authenticatedPrincipal.getName());
        List<String> roles = (List) authenticatedPrincipal.getAttribute(SystemConstants.ROLES);
        if (CollectionUtils.isNotEmpty(roles)) {
            details.setRoles(new HashSet(roles));
        }
        details.setAvatar((String) authenticatedPrincipal.getAttribute(SystemConstants.AVATAR));
        details.setEmployeeId((String) authenticatedPrincipal.getAttribute(SystemConstants.EMPLOYEE_ID));
        return details;
    }

    public static UserPrincipal toPrincipalDetails(Jwt jwt) {
        UserPrincipal details = new UserPrincipal();
        details.setId(jwt.getClaimAsString(SystemConstants.SCOPE_OPENID));
        details.setName(jwt.getClaimAsString("sub"));
        details.setRoles((Set) jwt.getClaim(SystemConstants.ROLES));
        details.setAvatar(jwt.getClaimAsString(SystemConstants.AVATAR));
        details.setEmployeeId(jwt.getClaimAsString(SystemConstants.EMPLOYEE_ID));
        return details;
    }
}
