package com.pigx.engine.autoconfigure.oauth2.definition;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/definition/UserPrincipalConverter.class */
public class UserPrincipalConverter {
    public static UserPrincipal toUserPrincipal(HerodotusUser herodotusUser) {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(herodotusUser.getUserId());
        userPrincipal.setName(herodotusUser.getUsername());
        userPrincipal.setRoles(herodotusUser.getRoles());
        userPrincipal.setAvatar(herodotusUser.getAvatar());
        userPrincipal.setEmployeeId(herodotusUser.getEmployeeId());
        return userPrincipal;
    }

    public static UserPrincipal toUserPrincipal(OAuth2AuthenticatedPrincipal oauth2AuthenticatedPrincipal) {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId((String) oauth2AuthenticatedPrincipal.getAttribute(SystemConstants.SCOPE_OPENID));
        userPrincipal.setName(oauth2AuthenticatedPrincipal.getName());
        List<String> roles = (List) oauth2AuthenticatedPrincipal.getAttribute(SystemConstants.ROLES);
        if (CollectionUtils.isNotEmpty(roles)) {
            userPrincipal.setRoles(new HashSet(roles));
        }
        userPrincipal.setAvatar((String) oauth2AuthenticatedPrincipal.getAttribute(SystemConstants.AVATAR));
        userPrincipal.setEmail((String) oauth2AuthenticatedPrincipal.getAttribute(SystemConstants.SCOPE_EMAIL));
        userPrincipal.setEmployeeId((String) oauth2AuthenticatedPrincipal.getAttribute(SystemConstants.EMPLOYEE_ID));
        return userPrincipal;
    }

    public static UserPrincipal toUserPrincipal(Jwt jwt) {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(jwt.getClaimAsString(SystemConstants.SCOPE_OPENID));
        userPrincipal.setName(jwt.getClaimAsString("sub"));
        userPrincipal.setRoles((Set) jwt.getClaim(SystemConstants.ROLES));
        userPrincipal.setEmail((String) jwt.getClaim(SystemConstants.SCOPE_EMAIL));
        userPrincipal.setAvatar(jwt.getClaimAsString(SystemConstants.AVATAR));
        userPrincipal.setEmployeeId(jwt.getClaimAsString(SystemConstants.EMPLOYEE_ID));
        return userPrincipal;
    }
}
