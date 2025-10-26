package com.pigx.engine.logic.upms.entity.security;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.pigx.engine.logic.upms.domain.deserializer.SysEmployeeEmptyToNull;
import com.pigx.engine.logic.upms.entity.hr.SysEmployee;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_USER)
@Schema(name = "系统用户")
@Cacheable
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
@Table(name = "sys_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})}, indexes = {@Index(name = "sys_user_id_idx", columnList = "user_id"), @Index(name = "sys_user_unm_idx", columnList = "user_name")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/security/SysUser.class */
public class SysUser extends AbstractSysEntity {

    @Id
    @Schema(name = "用户ID")
    @UuidGenerator
    @Column(name = "user_id", length = 64)
    private String userId;

    @Column(name = "user_name", length = 128, unique = true)
    @Schema(name = "用户名")
    private String username;

    @Column(name = SystemConstants.PASSWORD, length = 256)
    @Schema(name = "密码", description = "BCryptPasswordEncoder")
    private String password;

    @Column(name = "nick_name", length = 64)
    @Schema(name = "昵称")
    private String nickname;

    @Column(name = "phone_number", length = 256)
    @Schema(name = "手机号码")
    private String phoneNumber;

    @Column(name = SystemConstants.AVATAR, length = 1024)
    @Schema(name = "头像")
    private String avatar;

    @Column(name = SystemConstants.SCOPE_EMAIL, length = 100)
    @Schema(name = "EMAIL")
    private String email;

    @Column(name = "account_expire_at")
    @Schema(name = "账户过期日期")
    private LocalDateTime accountExpireAt;

    @Column(name = "credentials_expire_at")
    @Schema(name = "密码过期日期")
    private LocalDateTime credentialsExpireAt;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_ROLE)
    @Schema(name = "用户角色")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}, indexes = {@Index(name = "sys_user_role_uid_idx", columnList = "user_id"), @Index(name = "sys_user_role_rid_idx", columnList = "role_id")})
    @Fetch(FetchMode.SUBSELECT)
    private Set<SysRole> roles = new HashSet();

    @OneToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_EMPLOYEE)
    @JsonDeserialize(using = SysEmployeeEmptyToNull.class)
    @Schema(name = "人员")
    @JoinTable(name = "sys_user_employee", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "employee_id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "employee_id"})}, indexes = {@Index(name = "sys_user_employee_sid_idx", columnList = "user_id"), @Index(name = "sys_user_employee_eid_idx", columnList = "employee_id")})
    private SysEmployee employee;

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<SysRole> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    public SysEmployee getEmployee() {
        return this.employee;
    }

    public void setEmployee(SysEmployee employee) {
        this.employee = employee;
    }

    public LocalDateTime getAccountExpireAt() {
        return this.accountExpireAt;
    }

    public void setAccountExpireAt(LocalDateTime accountExpireAt) {
        this.accountExpireAt = accountExpireAt;
    }

    public LocalDateTime getCredentialsExpireAt() {
        return this.credentialsExpireAt;
    }

    public void setCredentialsExpireAt(LocalDateTime credentialsExpireAt) {
        this.credentialsExpireAt = credentialsExpireAt;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("userId", this.userId).add(SystemConstants.USERNAME, this.username).add(SystemConstants.PASSWORD, this.password).add("nickname", this.nickname).add("phoneNumber", this.phoneNumber).add(SystemConstants.AVATAR, this.avatar).add(SystemConstants.SCOPE_EMAIL, this.email).toString();
    }
}
