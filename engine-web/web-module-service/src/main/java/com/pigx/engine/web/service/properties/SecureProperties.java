package com.pigx.engine.web.service.properties;

import com.pigx.engine.core.definition.constant.BaseConstants;
import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = BaseConstants.PROPERTY_PREFIX_SECURE)
/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/properties/SecureProperties.class */
public class SecureProperties {
    private Idempotent idempotent = new Idempotent();
    private AccessLimited accessLimited = new AccessLimited();

    public Idempotent getIdempotent() {
        return this.idempotent;
    }

    public void setIdempotent(Idempotent idempotent) {
        this.idempotent = idempotent;
    }

    public AccessLimited getAccessLimited() {
        return this.accessLimited;
    }

    public void setAccessLimited(AccessLimited accessLimited) {
        this.accessLimited = accessLimited;
    }

    /* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/properties/SecureProperties$Idempotent.class */
    public static class Idempotent implements Serializable {
        private Duration expire = Duration.ofSeconds(5);

        public Duration getExpire() {
            return this.expire;
        }

        public void setExpire(Duration expire) {
            this.expire = expire;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("expire", this.expire).toString();
        }
    }

    /* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/properties/SecureProperties$AccessLimited.class */
    public static class AccessLimited implements Serializable {
        private int maxTimes = 10;
        private Duration expire = Duration.ofSeconds(30);

        public int getMaxTimes() {
            return this.maxTimes;
        }

        public void setMaxTimes(int maxTimes) {
            this.maxTimes = maxTimes;
        }

        public Duration getExpire() {
            return this.expire;
        }

        public void setExpire(Duration expire) {
            this.expire = expire;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("maxTimes", this.maxTimes).add("expire", this.expire).toString();
        }
    }
}
