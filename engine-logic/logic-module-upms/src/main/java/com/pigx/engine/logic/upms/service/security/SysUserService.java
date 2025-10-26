package com.pigx.engine.logic.upms.service.security;

import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.core.identity.enums.AccountCategory;
import com.pigx.engine.core.identity.utils.SecurityUtils;
import com.pigx.engine.data.core.enums.DataItemStatus;
import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.data.core.jpa.service.AbstractJpaService;
import com.pigx.engine.logic.upms.converter.SysUserToHerodotusUserConverter;
import com.pigx.engine.logic.upms.definition.SocialUserDetails;
import com.pigx.engine.logic.upms.entity.security.SysDefaultRole;
import com.pigx.engine.logic.upms.entity.security.SysRole;
import com.pigx.engine.logic.upms.entity.security.SysUser;
import com.pigx.engine.logic.upms.repository.security.SysUserRepository;
import cn.hutool.v7.core.data.id.IdUtil;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/service/security/SysUserService.class */
public class SysUserService extends AbstractJpaService<SysUser, String> {
    private static final Logger log = LoggerFactory.getLogger(SysUserService.class);
    private final Converter<SysUser, HerodotusUser> toUser = new SysUserToHerodotusUserConverter();
    private final SysUserRepository sysUserRepository;
    private final SysDefaultRoleService sysDefaultRoleService;

    public SysUserService(SysUserRepository sysUserRepository, SysDefaultRoleService sysDefaultRoleService) {
        this.sysUserRepository = sysUserRepository;
        this.sysDefaultRoleService = sysDefaultRoleService;
    }

    @Override // com.pigx.engine.data.core.jpa.service.BaseJpaReadableService
    public BaseJpaRepository<SysUser, String> getRepository() {
        return this.sysUserRepository;
    }

    public SysUser findByUsername(String username) {
        return this.sysUserRepository.findByUsername(username);
    }

    public SysUser findByUserId(String userId) {
        return this.sysUserRepository.findByUserId(userId);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SysUser changePassword(String userId, String password) {
        SysUser sysUser = findByUserId(userId);
        sysUser.setPassword(SecurityUtils.encrypt(password));
        return (SysUser) saveAndFlush(sysUser);
    }

    public SysUser assign(String userId, String[] roleIds) {
        SysUser sysUser = findByUserId(userId);
        return register(sysUser, roleIds);
    }

    public SysUser register(SysUser sysUser, String[] roleIds) {
        Set<SysRole> sysRoles = new HashSet<>();
        for (String roleId : roleIds) {
            SysRole sysRole = new SysRole();
            sysRole.setRoleId(roleId);
            sysRoles.add(sysRole);
        }
        return register(sysUser, sysRoles);
    }

    public SysUser register(SysUser sysUser, AccountCategory source) {
        SysDefaultRole sysDefaultRole = this.sysDefaultRoleService.findByScene(source);
        if (ObjectUtils.isNotEmpty(sysDefaultRole)) {
            SysRole sysRole = sysDefaultRole.getRole();
            if (ObjectUtils.isNotEmpty(sysRole)) {
                return register(sysUser, sysRole);
            }
        }
        log.error("[Herodotus] |- Default role for [{}] is not set correct, may case register error!", source);
        return null;
    }

    public SysUser register(SysUser sysUser, SysRole sysRole) {
        Set<SysRole> sysRoles = new HashSet<>();
        sysRoles.add(sysRole);
        return register(sysUser, sysRoles);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SysUser register(SysUser sysUser, Set<SysRole> sysRoles) {
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            sysUser.setRoles(sysRoles);
        }
        return (SysUser) saveAndFlush(sysUser);
    }

    private String enhance(String username) {
        if (StringUtils.isNotBlank(username)) {
            SysUser checkedSysUser = findByUsername(username);
            if (ObjectUtils.isNotEmpty(checkedSysUser)) {
                return checkedSysUser.getUsername() + IdUtil.nanoId(6);
            }
            return username;
        }
        return "Herodotus" + IdUtil.nanoId(6);
    }

    public SysUser register(SocialUserDetails socialUserDetails) {
        SysUser sysUser = new SysUser();
        String username = enhance(socialUserDetails.getUsername());
        sysUser.setUsername(username);
        String nickname = socialUserDetails.getNickname();
        if (StringUtils.isNotBlank(nickname)) {
            sysUser.setNickname(nickname);
        }
        String phoneNumber = socialUserDetails.getPhoneNumber();
        if (StringUtils.isNotBlank(phoneNumber)) {
            sysUser.setPhoneNumber(SecurityUtils.encrypt(phoneNumber));
        }
        String avatar = socialUserDetails.getAvatar();
        if (StringUtils.isNotBlank(avatar)) {
            sysUser.setAvatar(avatar);
        }
        sysUser.setPassword(SecurityUtils.encrypt("herodotus-cloud"));
        return register(sysUser, AccountCategory.getAccountType(socialUserDetails.getSource()));
    }

    public HerodotusUser registerUserDetails(SocialUserDetails socialUserDetails) {
        SysUser newSysUser = register(socialUserDetails);
        return (HerodotusUser) this.toUser.convert(newSysUser);
    }

    public void changeStatus(String userId, DataItemStatus status) {
        SysUser sysUser = findByUserId(userId);
        if (ObjectUtils.isNotEmpty(sysUser)) {
            sysUser.setStatus(status);
            log.debug("[Herodotus] |- Change user [{}] status to [{}]", sysUser.getUsername(), status.name());
            save(sysUser);
        }
    }
}
