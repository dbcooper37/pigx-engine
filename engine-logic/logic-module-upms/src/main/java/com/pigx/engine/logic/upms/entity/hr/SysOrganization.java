package com.pigx.engine.logic.upms.entity.hr;

import com.google.common.base.MoreObjects;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.logic.upms.constants.LogicUpmsConstants;
import com.pigx.engine.logic.upms.enums.OrganizationCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;


@Schema(name = "单位")
@Entity
@Table(name = "sys_organization", indexes = {@Index(name = "sys_organization_id_idx", columnList = "organization_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = LogicUpmsConstants.REGION_SYS_ORGANIZATION)
public class SysOrganization extends AbstractSysEntity {

    @Schema(name = "单位ID")
    @Id
    @UuidGenerator
    @Column(name = "organization_id", length = 64)
    private String organizationId;

    @Schema(name = "单位名称")
    @Column(name = "organization_name", length = 1000)
    private String organizationName;

    @Schema(name = "4A标准单位ID")
    @Column(name = "a4_biz_org_id", length = 64)
    private String a4BizOrgId;

    @Schema(name = "标准单位代码")
    @Column(name = "biz_org_code", length = 60)
    private String bizOrgCode;

    @Schema(name = "标准单位说明")
    @Column(name = "biz_org_desc", length = 256)
    private String bizOrgDesc;

    @Schema(name = "标准单位ID")
    @Column(name = "biz_org_id", length = 64)
    private String bizOrgId;

    @Schema(name = "标准单位名称")
    @Column(name = "biz_org_name", length = 200)
    private String bizOrgName;

    @Schema(name = "标准单位类型")
    @Column(name = "biz_org_type", length = 30)
    private String bizOrgType;

    @Schema(name = "分区代码")
    @Column(name = "partition_code", length = 256)
    private String partitionCode;

    @Schema(name = "单位简称")
    @Column(name = "short_name", length = 200)
    private String shortName;

    @Schema(name = "上级单位ID")
    @Column(name = "parent_id", length = 64)
    private String parentId;

    @Schema(name = "机构类别")
    @Column(name = "category")
    @Enumerated(EnumType.ORDINAL)
    private OrganizationCategory category = OrganizationCategory.ENTERPRISE;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getA4BizOrgId() {
        return a4BizOrgId;
    }

    public void setA4BizOrgId(String a4BizOrgId) {
        this.a4BizOrgId = a4BizOrgId;
    }

    public String getBizOrgCode() {
        return bizOrgCode;
    }

    public void setBizOrgCode(String bizOrgCode) {
        this.bizOrgCode = bizOrgCode;
    }

    public String getBizOrgDesc() {
        return bizOrgDesc;
    }

    public void setBizOrgDesc(String bizOrgDesc) {
        this.bizOrgDesc = bizOrgDesc;
    }

    public String getBizOrgId() {
        return bizOrgId;
    }

    public void setBizOrgId(String bizOrgId) {
        this.bizOrgId = bizOrgId;
    }

    public String getBizOrgName() {
        return bizOrgName;
    }

    public void setBizOrgName(String bizOrgName) {
        this.bizOrgName = bizOrgName;
    }

    public String getBizOrgType() {
        return bizOrgType;
    }

    public void setBizOrgType(String bizOrgType) {
        this.bizOrgType = bizOrgType;
    }

    public String getPartitionCode() {
        return partitionCode;
    }

    public void setPartitionCode(String partitionCode) {
        this.partitionCode = partitionCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public OrganizationCategory getCategory() {
        return category;
    }

    public void setCategory(OrganizationCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("organizationId", organizationId)
                .add("organizationName", organizationName)
                .add("a4BizOrgId", a4BizOrgId)
                .add("bizOrgCode", bizOrgCode)
                .add("bizOrgDesc", bizOrgDesc)
                .add("bizOrgId", bizOrgId)
                .add("bizOrgName", bizOrgName)
                .add("bizOrgType", bizOrgType)
                .add("partitionCode", partitionCode)
                .add("shortName", shortName)
                .add("parentId", parentId)
                .add("category", category)
                .toString();
    }
}
