package com.pigx.engine.oauth2.persistence.sas.jpa.generator;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/generator/HerodotusAuthorizationConsentId.class */
public class HerodotusAuthorizationConsentId implements Serializable {
    private String registeredClientId;
    private String principalName;

    public HerodotusAuthorizationConsentId() {
    }

    public HerodotusAuthorizationConsentId(String registeredClientId, String principalName) {
        this.registeredClientId = registeredClientId;
        this.principalName = principalName;
    }

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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusAuthorizationConsentId that = (HerodotusAuthorizationConsentId) o;
        return Objects.equal(this.registeredClientId, that.registeredClientId) && Objects.equal(this.principalName, that.principalName);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.registeredClientId, this.principalName});
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("registeredClientId", this.registeredClientId).add("principalName", this.principalName).toString();
    }
}
