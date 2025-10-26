package com.pigx.engine.logic.identity.entity;

import com.pigx.engine.logic.identity.definition.AbstractOAuth2RegisteredClient;
import com.pigx.engine.logic.identity.enums.ApplicationType;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
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
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_APPLICATION)
@Schema(name = "OAuth2应用实体")
@Cacheable
@Entity
@Table(name = "oauth2_application", indexes = {@Index(name = "oauth2_application_id_idx", columnList = "application_id"), @Index(name = "oauth2_application_cid_idx", columnList = "client_id")})
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/entity/OAuth2Application.class */
public class OAuth2Application extends AbstractOAuth2RegisteredClient {

    @Id
    @Schema(name = "应用ID")
    @UuidGenerator
    @Column(name = "application_id", length = 64)
    private String applicationId;

    @NotBlank(message = "应用名称不能为空")
    @Column(name = "application_name", length = 128)
    @Schema(name = "应用名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String applicationName;

    @Column(name = "abbreviation", length = 64)
    @Schema(name = "应用简称", title = "应用的简称、别名、缩写等信息")
    private String abbreviation;

    @Column(name = "logo", length = 1024)
    @Schema(name = "Logo", title = "Logo存储信息，可以是URL或者路径等")
    private String logo;

    @Column(name = "homepage", length = 1024)
    @Schema(name = "主页信息", title = "应用相关的主页信息方便查询")
    private String homepage;

    @Column(name = "application_type")
    @Enumerated(EnumType.ORDINAL)
    @Schema(name = "应用类型", title = "用于区分不同类型的应用")
    private ApplicationType applicationType = ApplicationType.WEB;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_APPLICATION_SCOPE)
    @Schema(name = "应用对应Scope", title = "传递应用对应Scope ID数组")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "oauth2_application_scope", joinColumns = {@JoinColumn(name = "application_id")}, inverseJoinColumns = {@JoinColumn(name = "scope_id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"application_id", "scope_id"})}, indexes = {@Index(name = "oauth2_application_scope_aid_idx", columnList = "application_id"), @Index(name = "oauth2_application_scope_sid_idx", columnList = "scope_id")})
    @Fetch(FetchMode.SUBSELECT)
    private Set<OAuth2Scope> scopes = new HashSet();

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public ApplicationType getApplicationType() {
        return this.applicationType;
    }

    public void setApplicationType(ApplicationType applicationType) {
        this.applicationType = applicationType;
    }

    @Override // com.pigx.engine.logic.identity.definition.AbstractOAuth2RegisteredClient
    public Set<OAuth2Scope> getScopes() {
        return this.scopes;
    }

    public void setScopes(Set<OAuth2Scope> scopes) {
        this.scopes = scopes;
    }

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public String getId() {
        return getApplicationId();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OAuth2Application that = (OAuth2Application) o;
        return Objects.equals(this.applicationId, that.applicationId);
    }

    public int hashCode() {
        return Objects.hash(this.applicationId);
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("applicationId", this.applicationId).add("applicationName", this.applicationName).add("abbreviation", this.abbreviation).add("logo", this.logo).add("homepage", this.homepage).add("applicationType", this.applicationType).toString();
    }
}
