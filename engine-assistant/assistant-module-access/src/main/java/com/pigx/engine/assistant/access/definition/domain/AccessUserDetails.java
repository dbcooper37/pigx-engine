package com.pigx.engine.assistant.access.definition.domain;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import me.zhyd.oauth.enums.AuthUserGender;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/definition/domain/AccessUserDetails.class */
public class AccessUserDetails {

    @Schema(name = "用户第三方系统的唯一id", description = "在调用方集成该组件时，可以用uuid + source唯一确定一个用")
    private String uuid;

    @Schema(name = "用户名")
    private String username;

    @Schema(name = "用户昵称")
    private String nickname;

    @Schema(name = "用户头像")
    private String avatar;

    @Schema(name = "用户网址")
    private String blog;

    @Schema(name = "所在公司")
    private String company;

    @Schema(name = "位置")
    private String location;

    @Schema(name = "用户邮箱")
    private String email;

    @Schema(name = "用户邮箱")
    private String remark;

    @Schema(name = "性别")
    private AuthUserGender gender;

    @Schema(name = "第三方用户来源")
    private String source;

    @Schema(name = "用户的授权令牌")
    private String accessToken;

    @Schema(name = "第三方用户的授权令牌的有效期", description = "部分平台可能没有")
    private Integer expireIn;

    @Schema(name = "刷新令牌", description = "部分平台可能没有")
    private String refreshToken;

    @Schema(name = "第三方用户的刷新令牌的有效期", description = "部分平台可能没有")
    private Integer refreshTokenExpireIn;

    @Schema(name = "第三方用户授予的权限", description = "部分平台可能没有")
    private String scope;

    @Schema(name = "个别平台的授权信息", description = "部分平台可能没有")
    private String tokenType;

    @Schema(name = "第三方用户的 ID", description = "部分平台可能没有")
    private String uid;

    @Schema(name = "第三方用户的 open id", description = "部分平台可能没有")
    private String openId;

    @Schema(name = "个别平台的授权信息", description = "部分平台可能没有")
    private String accessCode;

    @Schema(name = "第三方用户的 union id", description = "部分平台可能没有")
    private String unionId;

    @Schema(name = "小程序Appid", description = "部分平台可能没有")
    private String appId;

    @Schema(name = "手机号码", description = "部分平台可能没有")
    private String phoneNumber;

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBlog() {
        return this.blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public AuthUserGender getGender() {
        return this.gender;
    }

    public void setGender(AuthUserGender gender) {
        this.gender = gender;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpireIn() {
        return this.expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getRefreshTokenExpireIn() {
        return this.refreshTokenExpireIn;
    }

    public void setRefreshTokenExpireIn(Integer refreshTokenExpireIn) {
        this.refreshTokenExpireIn = refreshTokenExpireIn;
    }

    public String getScope() {
        return this.scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOpenId() {
        return this.openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessCode() {
        return this.accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getUnionId() {
        return this.unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("uuid", this.uuid).add(SystemConstants.USERNAME, this.username).add("nickname", this.nickname).add(SystemConstants.AVATAR, this.avatar).add("blog", this.blog).add("company", this.company).add("location", this.location).add(SystemConstants.SCOPE_EMAIL, this.email).add("remark", this.remark).add("gender", this.gender).add(SystemConstants.SOURCE, this.source).add("accessToken", this.accessToken).add("expireIn", this.expireIn).add("refreshToken", this.refreshToken).add("refreshTokenExpireIn", this.refreshTokenExpireIn).add("scope", this.scope).add("tokenType", this.tokenType).add("uid", this.uid).add("openId", this.openId).add("accessCode", this.accessCode).add("unionId", this.unionId).add("appId", this.appId).add("phoneNumber", this.phoneNumber).toString();
    }
}
