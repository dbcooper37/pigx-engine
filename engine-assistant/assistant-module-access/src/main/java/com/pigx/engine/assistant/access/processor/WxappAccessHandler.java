package com.pigx.engine.assistant.access.processor;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.pigx.engine.assistant.access.definition.AccessHandler;
import com.pigx.engine.assistant.access.definition.domain.AccessResponse;
import com.pigx.engine.assistant.access.definition.domain.AccessUserDetails;
import com.pigx.engine.assistant.access.exception.AccessIdentityVerificationFailedException;
import com.pigx.engine.assistant.access.exception.AccessPreProcessFailedException;
import com.pigx.engine.core.identity.domain.AccessPrincipal;
import com.pigx.engine.core.identity.enums.AccountCategory;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/processor/WxappAccessHandler.class */
public class WxappAccessHandler implements AccessHandler {
    private final WxappProcessor wxappProcessor;

    public WxappAccessHandler(WxappProcessor wxappProcessor) {
        this.wxappProcessor = wxappProcessor;
    }

    @Override // com.pigx.engine.assistant.access.definition.AccessHandler
    public AccessResponse preProcess(String core, String... params) {
        WxMaJscode2SessionResult wxMaSession = this.wxappProcessor.login(core, params[0]);
        if (ObjectUtils.isNotEmpty(wxMaSession)) {
            AccessResponse accessResponse = new AccessResponse();
            accessResponse.setSession(wxMaSession);
            return accessResponse;
        }
        throw new AccessPreProcessFailedException("Wxapp login failed");
    }

    @Override // com.pigx.engine.assistant.access.definition.AccessHandler
    public AccessUserDetails loadUserDetails(String source, AccessPrincipal accessPrincipal) {
        WxMaUserInfo wxMaUserInfo = this.wxappProcessor.getUserInfo(accessPrincipal.getAppId(), accessPrincipal.getSessionKey(), accessPrincipal.getEncryptedData(), accessPrincipal.getIv());
        if (ObjectUtils.isNotEmpty(wxMaUserInfo)) {
            return convertWxMaUserInfoToAccessUserDetails(wxMaUserInfo, accessPrincipal);
        }
        throw new AccessIdentityVerificationFailedException("Can not find the userinfo from Wechat!");
    }

    private AccessUserDetails convertWxMaUserInfoToAccessUserDetails(WxMaUserInfo wxMaUserInfo, AccessPrincipal accessPrincipal) {
        AccessUserDetails accessUserDetails = new AccessUserDetails();
        accessUserDetails.setUuid(accessPrincipal.getOpenId());
        accessUserDetails.setUsername(wxMaUserInfo.getNickName());
        accessUserDetails.setNickname(wxMaUserInfo.getNickName());
        accessUserDetails.setAvatar(wxMaUserInfo.getAvatarUrl());
        accessUserDetails.setLocation(wxMaUserInfo.getCountry() + "/" + wxMaUserInfo.getProvince() + "/" + wxMaUserInfo.getCity());
        accessUserDetails.setSource(AccountCategory.WXAPP.name());
        accessUserDetails.setOpenId(accessPrincipal.getOpenId());
        accessUserDetails.setUnionId(accessPrincipal.getUnionId());
        accessUserDetails.setAppId(wxMaUserInfo.getWatermark().getAppid());
        accessUserDetails.setPhoneNumber(wxMaUserInfo.getWatermark().getAppid());
        return accessUserDetails;
    }
}
