package com.pigx.engine.assistant.access.properties;

import com.pigx.engine.assistant.access.constant.AccessConstants;
import com.pigx.engine.core.definition.constant.BaseConstants;
import com.google.common.base.MoreObjects;
import java.time.Duration;
import java.util.Map;
import me.zhyd.oauth.config.AuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = AccessConstants.PROPERTY_ACCESS_JUSTAUTH)
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/properties/JustAuthProperties.class */
public class JustAuthProperties {
    private Boolean enabled;
    private Duration timeout = Duration.ofMinutes(5);
    private Map<String, AuthConfig> configs;
    private String alipayPublicKey;

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Duration getTimeout() {
        return this.timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Map<String, AuthConfig> getConfigs() {
        return this.configs;
    }

    public void setConfigs(Map<String, AuthConfig> configs) {
        this.configs = configs;
    }

    public String getAlipayPublicKey() {
        return this.alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add(BaseConstants.PROPERTY_NAME_ENABLED, this.enabled).add("timeout", this.timeout).add("alipayPublicKey", this.alipayPublicKey).toString();
    }
}
