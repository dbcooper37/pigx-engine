package com.pigx.engine.logic.upms.handler;

import cn.hutool.v7.core.bean.BeanUtil;
import com.google.common.collect.ImmutableSet;
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
import com.pigx.engine.oauth2.core.exception.SocialCredentialsParameterBindingFailedException;
import com.pigx.engine.oauth2.core.exception.UsernameAlreadyExistsException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;


public class DefaultSocialAuthenticationHandler extends AbstractSocialAuthenticationHandler {

    private final SysUserService sysUserService;
    private final SysSocialUserService sysSocialUserService;
    private final AccessHandlerStrategyFactory accessHandlerStrategyFactory;

    private final Converter<SysUser, HerodotusUser> toUser;

    public DefaultSocialAuthenticationHandler(SysUserService sysUserService, SysSocialUserService sysSocialUserService, AccessHandlerStrategyFactory accessHandlerStrategyFactory) {
        this.sysUserService = sysUserService;
        this.sysSocialUserService = sysSocialUserService;
        this.accessHandlerStrategyFactory = accessHandlerStrategyFactory;
        this.toUser = new SysUserToHerodotusUserConverter();
    }

    @Override
    public SocialUserDetails identity(String source, AccessPrincipal accessPrincipal) throws AccessIdentityVerificationFailedException {
        AccessUserDetails accessUserDetails = accessHandlerStrategyFactory.findAccessUserDetails(source, accessPrincipal);

        if (BeanUtil.isNotEmpty(accessUserDetails)) {
            SysSocialUser sysSocialUser = new SysSocialUser();
            BeanUtil.copyProperties(accessUserDetails, sysSocialUser);
            return sysSocialUser;
        }

        throw new AccessIdentityVerificationFailedException("Access Identity Verification Failed!");
    }

    @Override
    public SocialUserDetails isUserExist(SocialUserDetails socialUserDetails) {
        String uuid = socialUserDetails.getUuid();
        String source = socialUserDetails.getSource();
        if (StringUtils.isNotBlank(uuid) && StringUtils.isNotBlank(source)) {
            return sysSocialUserService.findByUuidAndSource(uuid, source);
        }

        return null;
    }

    @Override
    public HerodotusUser register(SocialUserDetails socialUserDetails) throws UsernameAlreadyExistsException {
        return sysUserService.registerUserDetails(socialUserDetails);
    }

    @Override
    public void binding(String userId, SocialUserDetails socialUserDetails) throws SocialCredentialsParameterBindingFailedException {
        if (socialUserDetails instanceof SysSocialUser sysSocialUser) {
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userId);
            sysSocialUser.setUsers(ImmutableSet.<SysUser>builder().add(sysUser).build());
            sysSocialUserService.saveAndFlush(sysSocialUser);
        }
    }

    @Override
    public void additionalRegisterOperation(HerodotusUser herodotusUserDetails, SocialUserDetails socialUserDetails) {

    }

    @Override
    public HerodotusUser signIn(SocialUserDetails socialUserDetails) {
        if (socialUserDetails instanceof SysSocialUser sysSocialUser) {
            SysUser sysUser = sysSocialUser.getUsers().stream().findFirst().orElse(null);
            if (ObjectUtils.isNotEmpty(sysUser)) {
                return toUser.convert(sysUser);
            } else {
                return null;
            }
        }

        return null;
    }

    @Override
    public void additionalSignInOperation(HerodotusUser herodotusUserDetails, SocialUserDetails newSocialUserDetails, SocialUserDetails oldSocialUserDetails) {
        if (newSocialUserDetails instanceof SysSocialUser newSysSocialUser && oldSocialUserDetails instanceof SysSocialUser oldSysSocialUser) {
            setSocialUserInfo(oldSysSocialUser, newSysSocialUser.getAccessToken(), newSysSocialUser.getExpireIn(), newSysSocialUser.getRefreshToken(), newSysSocialUser.getRefreshTokenExpireIn(), newSysSocialUser.getScope(), newSysSocialUser.getTokenType(), newSysSocialUser.getUid(), newSysSocialUser.getOpenId(), newSysSocialUser.getAccessCode(), newSysSocialUser.getUnionId());
            sysSocialUserService.saveAndFlush(oldSysSocialUser);
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
