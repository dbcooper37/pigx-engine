package com.pigx.engine.logic.identity.entity;

import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import com.google.common.base.MoreObjects;
import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_SCOPE)
@Cacheable
@Entity
@Table(name = "oauth2_scope", uniqueConstraints = {@UniqueConstraint(columnNames = {"scope_code"})}, indexes = {@Index(name = "oauth2_scope_id_idx", columnList = "scope_id"), @Index(name = "oauth2_scope_code_idx", columnList = "scope_code")})
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/entity/OAuth2Scope.class */
public class OAuth2Scope extends AbstractSysEntity {

    @Id
    @Column(name = "scope_id", length = 64)
    @UuidGenerator
    private String scopeId;

    @Column(name = "scope_code", length = 128, unique = true)
    private String scopeCode;

    @Column(name = "scope_name", length = 128)
    private String scopeName;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_PERMISSION)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "oauth2_scope_permission", joinColumns = {@JoinColumn(name = "scope_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"scope_id", "permission_id"})}, indexes = {@Index(name = "oauth2_scope_permission_sid_idx", columnList = "scope_id"), @Index(name = "oauth2_scope_permission_pid_idx", columnList = "permission_id")})
    @Fetch(FetchMode.SUBSELECT)
    private Set<OAuth2Permission> permissions = new HashSet();

    public String getScopeId() {
        return this.scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeCode() {
        return this.scopeCode;
    }

    public void setScopeCode(String scopeCode) {
        this.scopeCode = scopeCode;
    }

    public String getScopeName() {
        return this.scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public Set<OAuth2Permission> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<OAuth2Permission> permissions) {
        this.permissions = permissions;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OAuth2Scope that = (OAuth2Scope) o;
        return new EqualsBuilder().append(getScopeId(), that.getScopeId()).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getScopeId()).toHashCode();
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("scopeId", this.scopeId).add("scopeCode", this.scopeCode).add("scopeName", this.scopeName).toString();
    }
}
