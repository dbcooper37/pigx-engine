package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysUser;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;


public interface SysUserRepository extends BaseJpaRepository<SysUser, String> {
    /**
     * 根据用户名查找SysUser
     *
     * @param username 用户名
     * @return {@link SysUser}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysUser findByUsername(String username);

    /**
     * 根据用户ID查找用户
     *
     * @param userId 用户ID
     * @return {@link SysUser}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysUser findByUserId(String userId);
}
