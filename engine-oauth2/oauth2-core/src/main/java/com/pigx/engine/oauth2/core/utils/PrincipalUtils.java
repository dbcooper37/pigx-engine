package com.pigx.engine.oauth2.core.utils;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;

import java.util.HashSet;
import java.util.List;


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
        details.setId(authenticatedPrincipal.getAttribute(SystemConstants.SCOPE_OPENID));
        details.setName(authenticatedPrincipal.getName());
        List<String> roles = authenticatedPrincipal.getAttribute(SystemConstants.ROLES);
        if (CollectionUtils.isNotEmpty(roles)) {
            details.setRoles(new HashSet<>(roles));
        }
        details.setAvatar(authenticatedPrincipal.getAttribute(SystemConstants.AVATAR));
        details.setEmployeeId(authenticatedPrincipal.getAttribute(SystemConstants.EMPLOYEE_ID));
        return details;
    }

    public static UserPrincipal toPrincipalDetails(Jwt jwt) {
        UserPrincipal details = new UserPrincipal();
        details.setId(jwt.getClaimAsString(SystemConstants.SCOPE_OPENID));
        details.setName(jwt.getClaimAsString(JwtClaimNames.SUB));
        details.setRoles(jwt.getClaim(SystemConstants.ROLES));
        details.setAvatar(jwt.getClaimAsString(SystemConstants.AVATAR));
        details.setEmployeeId(jwt.getClaimAsString(SystemConstants.EMPLOYEE_ID));
        return details;
    }
}
