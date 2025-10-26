package com.pigx.engine.core.properties;

import com.pigx.engine.cache.core.enums.CacheMethod;
import com.google.common.base.MoreObjects;
import java.time.Duration;

/* loaded from: cache-core-3.5.7.0.jar:cn/herodotus/engine/cache/core/properties/CacheSetting.class */
public class CacheSetting {
    private String area;
    private CacheMethod method = CacheMethod.BOTH;
    private Duration expire = Duration.ofHours(2);
    private Boolean sync = false;
    private Duration localExpire;
    private Integer localLimit;
    private Boolean penetrationProtect;
    private Duration penetrationProtectTimeout;

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public CacheMethod getMethod() {
        return this.method;
    }

    public void setMethod(CacheMethod method) {
        this.method = method;
    }

    public Duration getExpire() {
        return this.expire;
    }

    public void setExpire(Duration expire) {
        this.expire = expire;
    }

    public Boolean getSync() {
        return this.sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }

    public Duration getLocalExpire() {
        return this.localExpire;
    }

    public void setLocalExpire(Duration localExpire) {
        this.localExpire = localExpire;
    }

    public Integer getLocalLimit() {
        return this.localLimit;
    }

    public void setLocalLimit(Integer localLimit) {
        this.localLimit = localLimit;
    }

    public Boolean getPenetrationProtect() {
        return this.penetrationProtect;
    }

    public void setPenetrationProtect(Boolean penetrationProtect) {
        this.penetrationProtect = penetrationProtect;
    }

    public Duration getPenetrationProtectTimeout() {
        return this.penetrationProtectTimeout;
    }

    public void setPenetrationProtectTimeout(Duration penetrationProtectTimeout) {
        this.penetrationProtectTimeout = penetrationProtectTimeout;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("area", this.area).add("method", this.method).add("expire", this.expire).add("sync", this.sync).add("localExpire", this.localExpire).add("localLimit", this.localLimit).add("penetrationProtect", this.penetrationProtect).add("penetrationProtectTimeout", this.penetrationProtectTimeout).toString();
    }
}
