package com.pigx.engine.oauth2.persistence.sas.jpa.entity;

import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import com.pigx.engine.oauth2.persistence.sas.jpa.definition.AbstractRegisteredClient;
import com.pigx.engine.oauth2.persistence.sas.jpa.generator.HerodotusRegisteredClientIdGenerator;
import com.google.common.base.MoreObjects;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.util.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_REGISTERED_CLIENT)
@Cacheable
@Entity
@Table(name = "oauth2_registered_client", indexes = {@Index(name = "oauth2_registered_client_id_idx", columnList = "id"), @Index(name = "oauth2_registered_client_cid_idx", columnList = "client_id")})
/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/entity/HerodotusRegisteredClient.class */
public class HerodotusRegisteredClient extends AbstractRegisteredClient {

    @Id
    @HerodotusRegisteredClientIdGenerator
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @Column(name = "client_id", nullable = false, length = 100)
    private String clientId;

    @Column(name = "client_secret", length = 200)
    private String clientSecret;

    @Column(name = "client_name", nullable = false, length = 200)
    private String clientName;

    @Column(name = "scopes", nullable = false, length = 1000)
    private String scopes;

    @Column(name = "client_settings", nullable = false, length = 2000)
    private String clientSettings;

    @Column(name = "token_settings", nullable = false, length = 2000)
    private String tokenSettings;

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override // com.pigx.engine.core.identity.domain.RegisteredClientDetails
    public String getClientSecret() {
        return this.clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getScopes() {
        return this.scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getClientSettings() {
        return this.clientSettings;
    }

    public void setClientSettings(String clientSettings) {
        this.clientSettings = clientSettings;
    }

    public String getTokenSettings() {
        return this.tokenSettings;
    }

    public void setTokenSettings(String tokenSettings) {
        this.tokenSettings = tokenSettings;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusRegisteredClient that = (HerodotusRegisteredClient) o;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", this.id).add("clientId", this.clientId).add("clientSecret", this.clientSecret).add("clientName", this.clientName).add("scopes", this.scopes).add("clientSettings", this.clientSettings).add("tokenSettings", this.tokenSettings).toString();
    }
}
