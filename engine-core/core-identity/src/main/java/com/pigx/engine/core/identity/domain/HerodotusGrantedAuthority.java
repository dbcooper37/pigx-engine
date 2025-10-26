package com.pigx.engine.core.identity.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.security.core.GrantedAuthority;

/* loaded from: core-identity-3.5.7.0.jar:cn/herodotus/engine/core/identity/domain/HerodotusGrantedAuthority.class */
public class HerodotusGrantedAuthority implements GrantedAuthority {
    private String authority;

    public HerodotusGrantedAuthority() {
    }

    public HerodotusGrantedAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HerodotusGrantedAuthority that = (HerodotusGrantedAuthority) o;
        return Objects.equal(this.authority, that.authority);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.authority});
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("authority", this.authority).toString();
    }
}
