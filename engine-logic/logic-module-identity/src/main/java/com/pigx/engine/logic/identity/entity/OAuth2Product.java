package com.pigx.engine.logic.identity.entity;

import com.google.common.base.MoreObjects;
import com.pigx.engine.data.core.jpa.entity.AbstractSysEntity;
import com.pigx.engine.oauth2.core.constants.OAuth2Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.UuidGenerator;


@Schema(name = "物联网产品")
@Entity
@Table(name = "oauth2_product", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_key"})},
        indexes = {@Index(name = "oauth2_product_pid_idx", columnList = "product_id"), @Index(name = "oauth2_product_ipk_idx", columnList = "product_key")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = OAuth2Constants.REGION_OAUTH2_PRODUCT)
public class OAuth2Product extends AbstractSysEntity {

    @Id
    @UuidGenerator
    @Column(name = "product_id", length = 64)
    private String productId;

    @Column(name = "product_key", length = 32, unique = true)
    private String productKey;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("productId", productId)
                .add("productKey", productKey)
                .toString();
    }
}
