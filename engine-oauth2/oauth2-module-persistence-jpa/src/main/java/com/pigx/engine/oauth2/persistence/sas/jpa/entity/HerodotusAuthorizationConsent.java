package com.pigx.engine.oauth2.persistence.sas.jpa.entity;

import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.definition.domain.BaseEntity;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import com.pigx.engine.oauth2.persistence.sas.jpa.generator.HerodotusAuthorizationConsentId;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@IdClass(HerodotusAuthorizationConsentId.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_AUTHORIZATION_CONSENT)
@Cacheable
@Entity
@Table(name = "oauth2_authorization_consent", indexes = {@Index(name = "oauth2_authorization_consent_rcid_idx", columnList = "registered_client_id"), @Index(name = "oauth2_authorization_consent_pn_idx", columnList = "principal_name")})
/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/entity/HerodotusAuthorizationConsent.class */
public class HerodotusAuthorizationConsent implements BaseEntity {

    @Id
    @Column(name = "registered_client_id", nullable = false, length = 100)
    private String registeredClientId;

    @Id
    @Column(name = "principal_name", nullable = false, length = 200)
    private String principalName;

    @Column(name = SystemConstants.AUTHORITIES, nullable = false, length = 1000)
    private String authorities;

    public String getRegisteredClientId() {
        return this.registeredClientId;
    }

    public void setRegisteredClientId(String registeredClientId) {
        this.registeredClientId = registeredClientId;
    }

    public String getPrincipalName() {
        return this.principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusAuthorizationConsent that = (HerodotusAuthorizationConsent) o;
        return Objects.equal(this.registeredClientId, that.registeredClientId) && Objects.equal(this.principalName, that.principalName);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.registeredClientId, this.principalName});
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("registeredClientId", this.registeredClientId).add("principalName", this.principalName).add(SystemConstants.AUTHORITIES, this.authorities).toString();
    }
}
