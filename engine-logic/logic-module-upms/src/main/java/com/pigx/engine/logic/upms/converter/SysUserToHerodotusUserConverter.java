package com.pigx.engine.logic.upms.converter;

import com.pigx.engine.core.identity.domain.HerodotusGrantedAuthority;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.core.identity.utils.SecurityUtils;
import com.pigx.engine.data.core.enums.DataItemStatus;
import com.pigx.engine.logic.upms.entity.security.SysPermission;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import com.pigx.engine.logic.upms.entity.security.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


public class SysUserToHerodotusUserConverter implements Converter<SysUser, HerodotusUser> {
    @Override
    public HerodotusUser convert(SysUser sysUser) {

        Set<HerodotusGrantedAuthority> authorities = new HashSet<>();

        Set<String> roles = new HashSet<>();
        for (SysRole sysRole : sysUser.getRoles()) {
            roles.add(sysRole.getRoleCode());
            authorities.add(new HerodotusGrantedAuthority(SecurityUtils.wellFormRolePrefix(sysRole.getRoleCode())));
            Set<SysPermission> sysPermissions = sysRole.getPermissions();
            if (CollectionUtils.isNotEmpty(sysPermissions)) {
                sysPermissions.forEach(sysAuthority -> authorities.add(new HerodotusGrantedAuthority((sysAuthority.getPermissionCode()))));
            }
        }

        String employeeId = ObjectUtils.isNotEmpty(sysUser.getEmployee()) ? sysUser.getEmployee().getEmployeeId() : null;

        return new HerodotusUser(sysUser.getUserId(), sysUser.getUsername(), sysUser.getPassword(),
                isEnabled(sysUser),
                isAccountNonExpired(sysUser),
                isCredentialsNonExpired(sysUser),
                isNonLocked(sysUser),
                authorities, roles, employeeId, sysUser.getAvatar());
    }

    private boolean isEnabled(SysUser sysUser) {
        return sysUser.getStatus() != DataItemStatus.FORBIDDEN;
    }

    private boolean isNonLocked(SysUser sysUser) {
        return !(sysUser.getStatus() == DataItemStatus.LOCKING);
    }

    private boolean isNonExpired(LocalDateTime localDateTime) {
        if (ObjectUtils.isEmpty(localDateTime)) {
            return true;
        } else {
            return localDateTime.isAfter(LocalDateTime.now());
        }
    }

    private boolean isAccountNonExpired(SysUser sysUser) {
        if (sysUser.getStatus() == DataItemStatus.EXPIRED) {
            return false;
        }

        return isNonExpired(sysUser.getAccountExpireAt());
    }

    private boolean isCredentialsNonExpired(SysUser sysUser) {
        return isNonExpired(sysUser.getCredentialsExpireAt());
    }
}
