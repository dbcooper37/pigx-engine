package com.pigx.engine.core.identity.domain;

import java.security.Principal;


public interface HerodotusPrincipal extends Principal {

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    String getId();

    /**
     * 获取用户头像
     *
     * @return 用户头像
     */
    String getAvatar();

    /**
     * 获取用户 Email
     *
     * @return 用户 Email
     */
    String getEmail();
}
