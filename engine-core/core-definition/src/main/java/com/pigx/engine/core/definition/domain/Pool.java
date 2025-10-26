package com.pigx.engine.core.definition.domain;

import com.google.common.base.MoreObjects;
import java.time.Duration;
import org.apache.commons.pool2.impl.BaseObjectPoolConfig;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/Pool.class */
public class Pool {
    private Integer maxTotal = 8;
    private Integer maxIdle = 8;
    private Integer minIdle = 0;
    private Boolean lifo = true;
    private Duration maxWait = BaseObjectPoolConfig.DEFAULT_MAX_WAIT;
    private Boolean blockWhenExhausted = true;
    private Duration minEvictableIdleDuration = BaseObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_DURATION;
    private Duration softMinEvictableIdleDuration = BaseObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_DURATION;

    public Integer getMaxTotal() {
        return this.maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return this.maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return this.minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Boolean getLifo() {
        return this.lifo;
    }

    public void setLifo(Boolean lifo) {
        this.lifo = lifo;
    }

    public Duration getMaxWait() {
        return this.maxWait;
    }

    public void setMaxWait(Duration maxWait) {
        this.maxWait = maxWait;
    }

    public Boolean getBlockWhenExhausted() {
        return this.blockWhenExhausted;
    }

    public void setBlockWhenExhausted(Boolean blockWhenExhausted) {
        this.blockWhenExhausted = blockWhenExhausted;
    }

    public Duration getMinEvictableIdleDuration() {
        return this.minEvictableIdleDuration;
    }

    public void setMinEvictableIdleDuration(Duration minEvictableIdleDuration) {
        this.minEvictableIdleDuration = minEvictableIdleDuration;
    }

    public Duration getSoftMinEvictableIdleDuration() {
        return this.softMinEvictableIdleDuration;
    }

    public void setSoftMinEvictableIdleDuration(Duration softMinEvictableIdleDuration) {
        this.softMinEvictableIdleDuration = softMinEvictableIdleDuration;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("maxTotal", this.maxTotal).add("maxIdle", this.maxIdle).add("minIdle", this.minIdle).add("lifo", this.lifo).add("maxWait", this.maxWait).add("blockWhenExhausted", this.blockWhenExhausted).add("minEvictableIdleTime", this.minEvictableIdleDuration).add("softMinEvictableIdleTime", this.softMinEvictableIdleDuration).toString();
    }
}
