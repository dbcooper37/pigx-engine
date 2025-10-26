package com.pigx.engine.logic.identity.entity;

import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
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

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_PRODUCT)
@Schema(name = "物联网产品")
@Cacheable
@Entity
@Table(name = "oauth2_product", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_key"})}, indexes = {@Index(name = "oauth2_product_pid_idx", columnList = "product_id"), @Index(name = "oauth2_product_ipk_idx", columnList = "product_key")})
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/entity/OAuth2Product.class */
public class OAuth2Product extends AbstractSysEntity {

    @Id
    @Column(name = "product_id", length = 64)
    @UuidGenerator
    private String productId;

    @Column(name = "product_key", length = 32, unique = true)
    private String productKey;

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductKey() {
        return this.productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    @Override // com.pigx.engine.data.core.jpa.entity.AbstractSysEntity, com.pigx.engine.data.core.jpa.entity.AbstractAuditEntity, com.pigx.engine.data.core.jpa.entity.AbstractEntity
    public String toString() {
        return MoreObjects.toStringHelper(this).add("productId", this.productId).add("productKey", this.productKey).toString();
    }
}
