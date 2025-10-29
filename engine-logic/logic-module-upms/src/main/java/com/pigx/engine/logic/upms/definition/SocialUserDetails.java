package com.pigx.engine.logic.upms.definition;


public interface SocialUserDetails {

    /**
     * 获取社交登录唯一标识
     *
     * @return String
     */
    String getUuid();

    /**
     * 获取社交登录分类标识
     *
     * @return String
     */
    String getSource();

    String getPhoneNumber();

    String getAvatar();

    String getUsername();

    String getNickname();
}
