package com.pigx.engine.core.identity.utils;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/utils/ServletSecurityUtils.class */
public class ServletSecurityUtils {
    public static UserPrincipal getUserPrincipal(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (ObjectUtils.isNotEmpty(session)) {
            Object object = session.getAttribute(SystemConstants.KEY__USER_PRINCIPAL);
            if (object instanceof UserPrincipal) {
                return (UserPrincipal) object;
            }
            return null;
        }
        return null;
    }

    public static Optional<UserPrincipal> findUserPrincipal(HttpServletRequest request) {
        return Optional.ofNullable(getUserPrincipal(request));
    }
}
