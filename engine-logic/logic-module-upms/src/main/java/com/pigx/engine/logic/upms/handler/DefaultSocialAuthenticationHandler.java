package com.pigx.engine.logic.upms.handler;

import com.pigx.engine.assistant.access.definition.domain.AccessUserDetails;
import com.pigx.engine.assistant.access.exception.AccessIdentityVerificationFailedException;
import com.pigx.engine.assistant.access.factory.AccessHandlerStrategyFactory;
import com.pigx.engine.core.identity.domain.AccessPrincipal;
import com.pigx.engine.core.identity.domain.HerodotusUser;
import com.pigx.engine.logic.upms.converter.SysUserToHerodotusUserConverter;
import com.pigx.engine.logic.upms.definition.AbstractSocialAuthenticationHandler;
import com.pigx.engine.logic.upms.definition.SocialUserDetails;
import com.pigx.engine.logic.upms.entity.security.SysSocialUser;
import com.pigx.engine.logic.upms.entity.security.SysUser;
import com.pigx.engine.logic.upms.service.security.SysSocialUserService;
import com.pigx.engine.logic.upms.service.security.SysUserService;
import cn.hutool.v7.core.bean.BeanUtil;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/handler/DefaultSocialAuthenticationHandler.class */
public class DefaultSocialAuthenticationHandler extends AbstractSocialAuthenticationHandler {
    private final SysUserService sysUserService;
    private final SysSocialUserService sysSocialUserService;
    private final AccessHandlerStrategyFactory accessHandlerStrategyFactory;
    private final Converter<SysUser, HerodotusUser> toUser = new SysUserToHerodotusUserConverter();

    public DefaultSocialAuthenticationHandler(SysUserService sysUserService, SysSocialUserService sysSocialUserService, AccessHandlerStrategyFactory accessHandlerStrategyFactory) {
        this.sysUserService = sysUserService;
        this.sysSocialUserService = sysSocialUserService;
        this.accessHandlerStrategyFactory = accessHandlerStrategyFactory;
    }

    @Override // com.pigx.engine.logic.upms.definition.AbstractSocialAuthenticationHandler
    public SocialUserDetails identity(String source, AccessPrincipal accessPrincipal) throws AccessIdentityVerificationFailedException {
        AccessUserDetails accessUserDetails = this.accessHandlerStrategyFactory.findAccessUserDetails(source, accessPrincipal);
        if (BeanUtil.isNotEmpty(accessUserDetails, new String[0])) {
            SysSocialUser sysSocialUser = new SysSocialUser();
            BeanUtil.copyProperties(accessUserDetails, sysSocialUser, new String[0]);
            return sysSocialUser;
        }
        throw new AccessIdentityVerificationFailedException("Access Identity Verification Failed!");
    }

    @Override // com.pigx.engine.logic.upms.definition.AbstractSocialAuthenticationHandler
    public SocialUserDetails isUserExist(SocialUserDetails socialUserDetails) {
        String uuid = socialUserDetails.getUuid();
        String source = socialUserDetails.getSource();
        if (StringUtils.isNotBlank(uuid) && StringUtils.isNotBlank(source)) {
            return this.sysSocialUserService.findByUuidAndSource(uuid, source);
        }
        return null;
    }

    /* JADX WARN: Byte code manipulation detected: skipped illegal throws declarations: [com.pigx.engine.oauth2.core.exception.UsernameAlreadyExistsException] */
    @Override // com.pigx.engine.logic.upms.definition.AbstractSocialAuthenticationHandler
    public HerodotusUser register(SocialUserDetails socialUserDetails) {
        return this.sysUserService.registerUserDetails(socialUserDetails);
    }

    /* JADX WARN: Byte code manipulation detected: skipped illegal throws declarations: [com.pigx.engine.oauth2.core.exception.SocialCredentialsParameterBindingFailedException] */
    @Override // com.pigx.engine.logic.upms.definition.AbstractSocialAuthenticationHandler
    public void binding(String userId, SocialUserDetails socialUserDetails) {
        if (socialUserDetails instanceof SysSocialUser) {
            SysSocialUser sysSocialUser = (SysSocialUser) socialUserDetails;
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userId);
            sysSocialUser.setUsers(ImmutableSet.builder().add(sysUser).build());
            this.sysSocialUserService.saveAndFlush(sysSocialUser);
        }
    }

    @Override // com.pigx.engine.logic.upms.definition.AbstractSocialAuthenticationHandler
    public void additionalRegisterOperation(HerodotusUser herodotusUserDetails, SocialUserDetails socialUserDetails) {
    }

    @Override // com.pigx.engine.logic.upms.definition.AbstractSocialAuthenticationHandler
    public HerodotusUser signIn(SocialUserDetails socialUserDetails) {
        if (socialUserDetails instanceof SysSocialUser) {
            SysSocialUser sysSocialUser = (SysSocialUser) socialUserDetails;
            SysUser sysUser = sysSocialUser.getUsers().stream().findFirst().orElse(null);
            if (ObjectUtils.isNotEmpty(sysUser)) {
                return (HerodotusUser) this.toUser.convert(sysUser);
            }
            return null;
        }
        return null;
    }

    @Override // com.pigx.engine.logic.upms.definition.AbstractSocialAuthenticationHandler
    public void additionalSignInOperation(HerodotusUser herodotusUserDetails, SocialUserDetails newSocialUserDetails, SocialUserDetails oldSocialUserDetails) {
        if (newSocialUserDetails instanceof SysSocialUser) {
            SysSocialUser newSysSocialUser = (SysSocialUser) newSocialUserDetails;
            if (oldSocialUserDetails instanceof SysSocialUser) {
                SysSocialUser oldSysSocialUser = (SysSocialUser) oldSocialUserDetails;
                setSocialUserInfo(oldSysSocialUser, newSysSocialUser.getAccessToken(), newSysSocialUser.getExpireIn(), newSysSocialUser.getRefreshToken(), newSysSocialUser.getRefreshTokenExpireIn(), newSysSocialUser.getScope(), newSysSocialUser.getTokenType(), newSysSocialUser.getUid(), newSysSocialUser.getOpenId(), newSysSocialUser.getAccessCode(), newSysSocialUser.getUnionId());
                this.sysSocialUserService.saveAndFlush(oldSysSocialUser);
            }
        }
    }

    protected void setSocialUserInfo(SysSocialUser sysSocialUser, String accessToken, Integer expireIn, String refreshToken, Integer refreshTokenExpireIn, String scope, String tokenType, String uid, String openId, String accessCode, String unionId) {
        sysSocialUser.setAccessToken(accessToken);
        sysSocialUser.setExpireIn(expireIn);
        sysSocialUser.setRefreshToken(refreshToken);
        sysSocialUser.setRefreshTokenExpireIn(refreshTokenExpireIn);
        sysSocialUser.setScope(scope);
        sysSocialUser.setTokenType(tokenType);
        sysSocialUser.setUid(uid);
        sysSocialUser.setOpenId(openId);
        sysSocialUser.setAccessCode(accessCode);
        sysSocialUser.setUnionId(unionId);
    }
}
