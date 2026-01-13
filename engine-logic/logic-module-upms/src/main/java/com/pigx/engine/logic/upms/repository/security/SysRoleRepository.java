package com.pigx.engine.logic.upms.repository.security;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Repository for SysRole entity with optimized queries.
 * <p>
 * This repository provides methods to fetch roles with their relationships
 * eagerly loaded to avoid N+1 query problems.
 * </p>
 *
 * @author PigX Engine Team
 * @since 1.0.0
 */
public interface SysRoleRepository extends BaseJpaRepository<SysRole, String> {

    /**
     * 根据角色代码查找角色
     *
     * @param roleCode 角色代码
     * @return {@link SysRole}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysRole findByRoleCode(String roleCode);

    /**
     * 根据角色ID查询角色
     *
     * @param roleId 角色ID
     * @return {@link SysRole}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysRole findByRoleId(String roleId);

    /**
     * Finds a role by code with permissions eagerly loaded.
     * <p>
     * Use this method when you need to access role permissions to avoid N+1 queries.
     * </p>
     *
     * @param roleCode the role code to search for
     * @return Optional containing the role with permissions, or empty if not found
     */
    @EntityGraph(attributePaths = {"permissions"})
    @Query("SELECT r FROM SysRole r WHERE r.roleCode = :roleCode")
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SysRole> findByRoleCodeWithPermissions(@Param("roleCode") String roleCode);

    /**
     * Finds a role by ID with permissions eagerly loaded.
     *
     * @param roleId the role ID to search for
     * @return Optional containing the role with permissions
     */
    @EntityGraph(attributePaths = {"permissions"})
    @Query("SELECT r FROM SysRole r WHERE r.roleId = :roleId")
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SysRole> findByIdWithPermissions(@Param("roleId") String roleId);

    /**
     * Finds all roles with their permissions eagerly loaded.
     * <p>
     * Use this method when you need to display all roles with their permissions.
     * </p>
     *
     * @return List of all roles with permissions loaded
     */
    @EntityGraph(attributePaths = {"permissions"})
    @Query("SELECT DISTINCT r FROM SysRole r")
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    List<SysRole> findAllWithPermissions();

    /**
     * Finds roles by a set of role IDs with permissions eagerly loaded.
     *
     * @param roleIds the set of role IDs to search for
     * @return List of roles with permissions
     */
    @EntityGraph(attributePaths = {"permissions"})
    @Query("SELECT r FROM SysRole r WHERE r.roleId IN :roleIds")
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    List<SysRole> findByRoleIdInWithPermissions(@Param("roleIds") Set<String> roleIds);
}
