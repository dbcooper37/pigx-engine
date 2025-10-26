package com.pigx.engine.data.tenant.entity;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.data.tenant.constant.TenantConstants;
import com.google.common.base.MoreObjects;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = TenantConstants.REGION_SYS_TENANT_DATASOURCE)
@Schema(name = "多租户数据源管理")
@Cacheable
@Entity
@Table(name = "sys_tenant_datasource", uniqueConstraints = {@UniqueConstraint(columnNames = {"tenant_id"})}, indexes = {@Index(name = "sys_tenant_datasource_id_idx", columnList = "datasource_id")})
/* loaded from: data-module-tenant-3.5.7.0.jar:cn/herodotus/engine/data/tenant/entity/SysTenantDataSource.class */
public class SysTenantDataSource extends AbstractSysEntity {

    @Id
    @Schema(name = "租户数据源主键")
    @UuidGenerator
    @Column(name = "datasource_id", length = 64)
    private String datasourceId;

    @Column(name = "tenant_id", length = 64, unique = true)
    @Schema(name = "租户ID", description = "租户的唯一标识")
    private String tenantId;

    @Column(name = "user_name", length = 100)
    @Schema(name = "数据库用户名")
    private String username;

    @Column(name = SystemConstants.PASSWORD, length = 100)
    @Schema(name = "数据库密码")
    private String password;

    @Column(name = "driver_class_name", length = 64)
    @Schema(name = "数据库驱动")
    private String driverClassName;

    @Column(name = "url", length = 1000)
    @Schema(name = "数据库连接")
    private String url;

    @Schema(name = "是否已经初始化", description = "默认值 false")
    private Boolean initialize = false;

    public String getDatasourceId() {
        return this.datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getInitialize() {
        return this.initialize;
    }

    public void setInitialize(Boolean initialize) {
        this.initialize = initialize;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("datasourceId", this.datasourceId).add("tenantId", this.tenantId).add(SystemConstants.USERNAME, this.username).add(SystemConstants.PASSWORD, this.password).add("driverClassName", this.driverClassName).add("url", this.url).add("initialize", this.initialize).toString();
    }
}
