package com.pigx.engine.logic.upms.entity.hr;

import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.pigx.engine.logic.upms.enums.OrganizationCategory;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_ORGANIZATION)
@Schema(name = "单位")
@Cacheable
@Entity
@Table(name = "sys_organization", indexes = {@Index(name = "sys_organization_id_idx", columnList = "organization_id")})
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/entity/hr/SysOrganization.class */
public class SysOrganization extends AbstractSysEntity {

    @Id
    @Schema(name = "单位ID")
    @UuidGenerator
    @Column(name = "organization_id", length = 64)
    private String organizationId;

    @Column(name = "organization_name", length = 1000)
    @Schema(name = "单位名称")
    private String organizationName;

    @Column(name = "a4_biz_org_id", length = 64)
    @Schema(name = "4A标准单位ID")
    private String a4BizOrgId;

    @Column(name = "biz_org_code", length = ErrorCodeMapperBuilderOrdered.ACCESS)
    @Schema(name = "标准单位代码")
    private String bizOrgCode;

    @Column(name = "biz_org_desc", length = 256)
    @Schema(name = "标准单位说明")
    private String bizOrgDesc;

    @Column(name = "biz_org_id", length = 64)
    @Schema(name = "标准单位ID")
    private String bizOrgId;

    @Column(name = "biz_org_name", length = 200)
    @Schema(name = "标准单位名称")
    private String bizOrgName;

    @Column(name = "biz_org_type", length = ErrorCodeMapperBuilderOrdered.OAUTH2)
    @Schema(name = "标准单位类型")
    private String bizOrgType;

    @Column(name = "partition_code", length = 256)
    @Schema(name = "分区代码")
    private String partitionCode;

    @Column(name = "short_name", length = 200)
    @Schema(name = "单位简称")
    private String shortName;

    @Column(name = "parent_id", length = 64)
    @Schema(name = "上级单位ID")
    private String parentId;

    @Column(name = "category")
    @Enumerated(EnumType.ORDINAL)
    @Schema(name = "机构类别")
    private OrganizationCategory category = OrganizationCategory.ENTERPRISE;

    public String getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getA4BizOrgId() {
        return this.a4BizOrgId;
    }

    public void setA4BizOrgId(String a4BizOrgId) {
        this.a4BizOrgId = a4BizOrgId;
    }

    public String getBizOrgCode() {
        return this.bizOrgCode;
    }

    public void setBizOrgCode(String bizOrgCode) {
        this.bizOrgCode = bizOrgCode;
    }

    public String getBizOrgDesc() {
        return this.bizOrgDesc;
    }

    public void setBizOrgDesc(String bizOrgDesc) {
        this.bizOrgDesc = bizOrgDesc;
    }

    public String getBizOrgId() {
        return this.bizOrgId;
    }

    public void setBizOrgId(String bizOrgId) {
        this.bizOrgId = bizOrgId;
    }

    public String getBizOrgName() {
        return this.bizOrgName;
    }

    public void setBizOrgName(String bizOrgName) {
        this.bizOrgName = bizOrgName;
    }

    public String getBizOrgType() {
        return this.bizOrgType;
    }

    public void setBizOrgType(String bizOrgType) {
        this.bizOrgType = bizOrgType;
    }

    public String getPartitionCode() {
        return this.partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public OrganizationCategory getCategory() {
        return this.category;
    }

    public void setCategory(OrganizationCategory category) {
        this.category = category;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("organizationId", this.organizationId).add("organizationName", this.organizationName).add("a4BizOrgId", this.a4BizOrgId).add("bizOrgCode", this.bizOrgCode).add("bizOrgDesc", this.bizOrgDesc).add("bizOrgId", this.bizOrgId).add("bizOrgName", this.bizOrgName).add("bizOrgType", this.bizOrgType).add("partitionCode", this.partitionCode).add("shortName", this.shortName).add("parentId", this.parentId).add("category", this.category).toString();
    }
}
