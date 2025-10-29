package com.pigx.engine.core.identity.utils;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Optional;


public class ServletSecurityUtils {

    /**
     * 从 {@link HttpSession} 读取用户信息 {@link UserPrincipal}
     * <p>
     * 注意：该方法依赖于整体的 Session 环境，后端 Session 以及前端 Session 的配合。对于不支持 Session 的前端，该方法可能会获取不到值。
     *
     * @param request {@link HttpServletRequest}
     * @return 用户信息 {@link UserPrincipal}
     */
    public static UserPrincipal getUserPrincipal(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (ObjectUtils.isNotEmpty(session)) {
            Object object = session.getAttribute(SystemConstants.KEY__USER_PRINCIPAL);
            if (object instanceof UserPrincipal) {
                return (UserPrincipal) object;
            }
        }

        return null;
    }

    /**
     * 从 {@link HttpSession} 读取用户信息 {@link UserPrincipal}
     * <p>
     * 注意：该方法依赖于整体的 Session 环境，后端 Session 以及前端 Session 的配合。对于不支持 Session 的前端，该方法可能会获取不到值。
     *
     * @param request {@link HttpServletRequest}
     * @return optional {@link Optional}
     */
    public static Optional<UserPrincipal> findUserPrincipal(HttpServletRequest request) {
        return Optional.ofNullable(getUserPrincipal(request));
    }
}
