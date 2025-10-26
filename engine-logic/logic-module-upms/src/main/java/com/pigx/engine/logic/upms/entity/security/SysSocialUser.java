package com.pigx.engine.logic.upms.entity.security;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.pigx.engine.logic.upms.definition.SocialUserDetails;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import me.zhyd.oauth.enums.AuthUserGender;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_SOCIAL_USER)
@Schema(name = "社会化登录用户")
@Cacheable
@Entity
@Table(name = "sys_social_user", indexes = {@Index(name = "sys_social_user_id_idx", columnList = "social_id")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/security/SysSocialUser.class */
public class SysSocialUser extends AbstractSysEntity implements SocialUserDetails {

    @Id
    @Schema(name = "社会用户ID")
    @UuidGenerator
    @Column(name = "social_id", length = 64)
    private String socialId;

    @Column(name = "uuid", length = 64)
    @Schema(name = "用户第三方系统的唯一id", description = "在调用方集成该组件时，可以用uuid + source唯一确定一个用")
    private String uuid;

    @Column(name = "user_name", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "用户名")
    private String username;

    @Column(name = "nick_name", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "用户昵称")
    private String nickname;

    @Column(name = SystemConstants.AVATAR, length = 1000)
    @Schema(name = "用户头像")
    private String avatar;

    @Column(name = "blog", length = 100)
    @Schema(name = "用户网址")
    private String blog;

    @Column(name = "company", length = 256)
    @Schema(name = "所在公司")
    private String company;

    @Column(name = "location", length = 512)
    @Schema(name = "位置")
    private String location;

    @Column(name = SystemConstants.SCOPE_EMAIL, length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "用户邮箱")
    private String email;

    @Column(name = "remark", length = 512)
    @Schema(name = "用户邮箱")
    private String remark;

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    @Schema(name = "性别")
    private AuthUserGender gender;

    @Column(name = SystemConstants.SOURCE)
    @Schema(name = "第三方用户来源")
    private String source;

    @Column(name = "access_token", columnDefinition = "TEXT")
    @Schema(name = "用户的授权令牌")
    private String accessToken;

    @Column(name = "expire_in")
    @Schema(name = "第三方用户的授权令牌的有效期", description = "部分平台可能没有")
    private Integer expireIn;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    @Schema(name = "刷新令牌", description = "部分平台可能没有")
    private String refreshToken;

    @Column(name = "refresh_token_expire_in")
    @Schema(name = "第三方用户的刷新令牌的有效期", description = "部分平台可能没有")
    private Integer refreshTokenExpireIn;

    @Column(name = "scope", length = 1200)
    @Schema(name = "第三方用户授予的权限", description = "部分平台可能没有")
    private String scope;

    @Column(name = "token_type", length = 100)
    @Schema(name = "个别平台的授权信息", description = "部分平台可能没有")
    private String tokenType;

    @Column(name = "uid", length = 64)
    @Schema(name = "第三方用户的 ID", description = "部分平台可能没有")
    private String uid;

    @Column(name = "open_id", length = 64)
    @Schema(name = "第三方用户的 open id", description = "部分平台可能没有")
    private String openId;

    @Column(name = "access_code", length = 64)
    @Schema(name = "个别平台的授权信息", description = "部分平台可能没有")
    private String accessCode;

    @Column(name = "union_id", length = 64)
    @Schema(name = "第三方用户的 union id", description = "部分平台可能没有")
    private String unionId;

    @Column(name = "app_id", length = 64)
    @Schema(name = "小程序Appid", description = "部分平台可能没有")
    private String appId;

    @Column(name = "phone_number", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "手机号码", description = "部分平台可能没有")
    private String phoneNumber;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_USER)
    @Schema(name = "系统用户")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_social_sys_user", joinColumns = {@JoinColumn(name = "social_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"social_id", "user_id"})}, indexes = {@Index(name = "sys_social_sys_user_oid_idx", columnList = "social_id"), @Index(name = "sys_social_sys_user_uid_idx", columnList = "user_id")})
    @Fetch(FetchMode.SUBSELECT)
    private Set<SysUser> users = new HashSet();

    public String getSocialId() {
        return this.socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
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

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
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

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getExpireIn() {
        return this.expireIn;
    }

    public void setExpireIn(Integer expireIn) {
        this.expireIn = expireIn;
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

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<SysUser> getUsers() {
        return this.users;
    }

    public void setUsers(Set<SysUser> users) {
        this.users = users;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("socialId", this.socialId).add("uuid", this.uuid).add(SystemConstants.USERNAME, this.username).add("nickname", this.nickname).add(SystemConstants.AVATAR, this.avatar).add("blog", this.blog).add("company", this.company).add("location", this.location).add(SystemConstants.SCOPE_EMAIL, this.email).add("remark", this.remark).add("gender", this.gender).add(SystemConstants.SOURCE, this.source).add("accessToken", this.accessToken).add("expireIn", this.expireIn).add("refreshToken", this.refreshToken).add("refreshTokenExpireIn", this.refreshTokenExpireIn).add("scope", this.scope).add("tokenType", this.tokenType).add("uid", this.uid).add("openId", this.openId).add("accessCode", this.accessCode).add("unionId", this.unionId).add("appId", this.appId).add("users", this.users).toString();
    }
}
