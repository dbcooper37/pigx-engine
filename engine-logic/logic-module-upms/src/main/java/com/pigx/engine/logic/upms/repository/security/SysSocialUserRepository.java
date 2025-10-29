package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysSocialUser;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;


public interface SysSocialUserRepository extends BaseJpaRepository<SysSocialUser, String> {

    /**
     * 通过 uuid 和 source查询用户
     *
     * @param uuid   JustAuth返回信息中uuid，具体信息查询JustAuth {@see :https://justauth.wiki/quickstart/explain.html}
     * @param source 第三方登录类型的字符串
     * @return SysSocialUser
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysSocialUser findSysSocialUserByUuidAndSource(String uuid, String source);
}
