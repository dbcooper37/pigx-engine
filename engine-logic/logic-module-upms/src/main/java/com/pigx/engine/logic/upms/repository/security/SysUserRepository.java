package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysUser;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


/**
 * Repository for SysUser entity with optimized queries.
 * <p>
 * This repository provides methods to fetch users with their relationships
 * eagerly loaded to avoid N+1 query problems.
 * </p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
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

    /**
     * Finds a user by username with roles eagerly loaded.
     * <p>
     * Use this method when you need to access user roles to avoid N+1 queries.
     * </p>
     *
     * @param username the username to search for
     * @return Optional containing the user with roles, or empty if not found
     */
    @EntityGraph(attributePaths = {"roles"})
    @Query("SELECT u FROM SysUser u WHERE u.username = :username")
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SysUser> findByUsernameWithRoles(@Param("username") String username);

    /**
     * Finds a user by ID with roles and permissions eagerly loaded.
     * <p>
     * Use this method for authorization checks where you need full permission data.
     * </p>
     *
     * @param userId the user ID to search for
     * @return Optional containing the user with roles and permissions
     */
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    @Query("SELECT u FROM SysUser u WHERE u.userId = :userId")
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SysUser> findByIdWithRolesAndPermissions(@Param("userId") String userId);

    /**
     * Finds a user by username with roles and permissions eagerly loaded.
     * <p>
     * Use this method for authentication where you need full user details including permissions.
     * </p>
     *
     * @param username the username to search for
     * @return Optional containing the user with roles and permissions
     */
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    @Query("SELECT u FROM SysUser u WHERE u.username = :username")
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SysUser> findByUsernameWithRolesAndPermissions(@Param("username") String username);

    /**
     * Finds a user by username with employee information eagerly loaded.
     * <p>
     * Use this method when you need employee details along with user information.
     * </p>
     *
     * @param username the username to search for
     * @return Optional containing the user with employee data
     */
    @EntityGraph(attributePaths = {"employee"})
    @Query("SELECT u FROM SysUser u WHERE u.username = :username")
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SysUser> findByUsernameWithEmployee(@Param("username") String username);
}
