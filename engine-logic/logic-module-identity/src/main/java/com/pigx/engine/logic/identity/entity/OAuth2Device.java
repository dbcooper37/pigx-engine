package com.pigx.engine.logic.identity.entity;

import com.pigx.engine.logic.identity.definition.AbstractOAuth2RegisteredClient;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_DEVICE)
@Schema(name = "物联网设备")
@Cacheable
@Entity
@Table(name = "oauth2_device", uniqueConstraints = {@UniqueConstraint(columnNames = {"device_name"})}, indexes = {@Index(name = "oauth2_device_id_idx", columnList = "device_id"), @Index(name = "oauth2_device_ipk_idx", columnList = "device_name"), @Index(name = "oauth2_device_pid_idx", columnList = "product_id")})
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/entity/OAuth2Device.class */
public class OAuth2Device extends AbstractOAuth2RegisteredClient {

    @Id
    @Schema(name = "设备ID")
    @UuidGenerator
    @Column(name = "device_id", length = 64)
    private String deviceId;

    @Column(name = "device_name", length = 64, unique = true)
    @Schema(name = "设备名称")
    private String deviceName;

    @Column(name = "product_id", length = 64)
    @Schema(name = "产品ID")
    private String productId;

    @Column(name = "is_activated")
    @Schema(name = "是否已激活", title = "设备是否已经激活状态标记，默认值false，即未激活")
    private Boolean activated = Boolean.FALSE;

    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_APPLICATION_SCOPE)
    @Schema(name = "设备对应Scope", title = "传递设备对应Scope ID数组")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "oauth2_device_scope", joinColumns = {@JoinColumn(name = "device_id")}, inverseJoinColumns = {@JoinColumn(name = "scope_id")}, uniqueConstraints = {@UniqueConstraint(columnNames = {"device_id", "scope_id"})}, indexes = {@Index(name = "oauth2_device_scope_aid_idx", columnList = "device_id"), @Index(name = "oauth2_device_scope_sid_idx", columnList = "scope_id")})
    @Fetch(FetchMode.SUBSELECT)
    private Set<OAuth2Scope> scopes = new HashSet();

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
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
        return getDeviceId();
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("deviceId", this.deviceId).add("deviceName", this.deviceName).add("productId", this.productId).add("activated", this.activated).toString();
    }
}
