package com.pigx.engine.logic.upms.entity.hr;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.enums.AccountCategory;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.pigx.engine.logic.upms.definition.SocialUserDetails;
import com.pigx.engine.logic.upms.domain.deserializer.SysUserEmptyToNull;
import com.pigx.engine.logic.upms.entity.security.SysUser;
import com.pigx.engine.logic.upms.enums.Gender;
import com.pigx.engine.logic.upms.enums.Identity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_EMPLOYEE)
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = SystemConstants.EMPLOYEE_ID)
@Schema(name = "人员")
@Cacheable
@NamedEntityGraph(name = "SysEmployeeWithSysUser.Graph", attributeNodes = {@NamedAttributeNode(value = "user", subgraph = "SysUser.SubGraph")}, subgraphs = {@NamedSubgraph(name = "SysUser.SubGraph", attributeNodes = {@NamedAttributeNode("userId")})})
@Table(name = "sys_employee", indexes = {@Index(name = "sys_employee_id_idx", columnList = "employee_id")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/hr/SysEmployee.class */
public class SysEmployee extends AbstractSysEntity implements SocialUserDetails {

    @Id
    @Schema(name = "人员ID")
    @UuidGenerator
    @Column(name = "employee_id", length = 64)
    private String employeeId;

    @Column(name = "employee_name", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "姓名")
    private String employeeName;

    @Column(name = "employee_no", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "工号")
    private String employeeNo;

    @Column(name = "mobile_phone_number", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "手机号码")
    private String mobilePhoneNumber;

    @Column(name = "office_phone_number", length = ErrorCodeMapperBuilderOrdered.MESSAGE)
    @Schema(name = "办公电话")
    private String officePhoneNumber;

    @Column(name = SystemConstants.SCOPE_EMAIL, length = 100)
    @Schema(name = "电子邮箱")
    private String email;

    @Column(name = "pki_email", length = 100)
    @Schema(name = "PKI电子邮箱")
    private String pkiEmail;

    @Column(name = "a4_biz_emp_id", length = 256)
    @Schema(name = "4A标准人员ID")
    private String a4BizEmpId;

    @Column(name = SystemConstants.AVATAR, length = 1000)
    @Schema(name = "头像")
    private String avatar;

    @Column(name = "birth_day")
    @Schema(name = "生日")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    @Schema(name = "性别")
    private Gender gender = Gender.MAN;

    @Column(name = "identity")
    @Enumerated(EnumType.ORDINAL)
    @Schema(name = "身份")
    private Identity identity = Identity.STAFF;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_DEPARTMENT)
    @ManyToMany
    @JoinTable(name = "sys_employee_department", joinColumns = {@JoinColumn(name = "employee_id")}, inverseJoinColumns = {@JoinColumn(name = "department_id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "department_id"})}, indexes = {@Index(name = "sys_employee_department_eid_idx", columnList = "employee_id"), @Index(name = "sys_employee_department_did_idx", columnList = "department_id")})
    @Fetch(FetchMode.SUBSELECT)
    private Set<SysDepartment> departments = new HashSet();

    @OneToOne(mappedBy = "employee", cascade = {CascadeType.ALL})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_USER)
    @JsonDeserialize(using = SysUserEmptyToNull.class)
    private SysUser user;

    public SysUser getUser() {
        return this.user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNo() {
        return this.employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getMobilePhoneNumber() {
        return this.mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getOfficePhoneNumber() {
        return this.officePhoneNumber;
    }

    public void setOfficePhoneNumber(String officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPkiEmail() {
        return this.pkiEmail;
    }

    public void setPkiEmail(String pkiEmail) {
        this.pkiEmail = pkiEmail;
    }

    public String getA4BizEmpId() {
        return this.a4BizEmpId;
    }

    public void setA4BizEmpId(String a4BizEmpId) {
        this.a4BizEmpId = a4BizEmpId;
    }

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
    public String getUuid() {
        return this.employeeId;
    }

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
    public String getSource() {
        return AccountCategory.INSTITUTION.getKey();
    }

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
    public String getPhoneNumber() {
        return getMobilePhoneNumber();
    }

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
    public String getUsername() {
        return getEmployeeName();
    }

    @Override // com.pigx.engine.logic.upms.definition.SocialUserDetails
    public String getNickname() {
        return getEmail();
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Identity getIdentity() {
        return this.identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Set<SysDepartment> getDepartments() {
        return this.departments;
    }

    public void setDepartments(Set<SysDepartment> departments) {
        this.departments = departments;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysEmployee that = (SysEmployee) o;
        return new EqualsBuilder().append(getEmployeeId(), that.getEmployeeId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getEmployeeId()).toHashCode();
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add(SystemConstants.EMPLOYEE_ID, this.employeeId).add("employeeName", this.employeeName).add("employeeNo", this.employeeNo).add("mobilePhoneNumber", this.mobilePhoneNumber).add("officePhoneNumber", this.officePhoneNumber).add(SystemConstants.SCOPE_EMAIL, this.email).add("pkiEmail", this.pkiEmail).add("a4BizEmpId", this.a4BizEmpId).add(SystemConstants.AVATAR, this.avatar).add("birthday", this.birthday).add("gender", this.gender).add("identity", this.identity).toString();
    }
}
