package com.pigx.engine.assistant.access.definition.domain;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/definition/domain/AccessResponse.class */
public class AccessResponse {
    private String authorizeUrl;
    private Boolean success;
    private WxMaJscode2SessionResult session;

    public String getAuthorizeUrl() {
        return this.authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public WxMaJscode2SessionResult getSession() {
        return this.session;
    }

    public void setSession(WxMaJscode2SessionResult session) {
        this.session = session;
    }
}
