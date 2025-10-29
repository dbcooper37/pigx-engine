
package com.pigx.engine.assistant.access.properties;

import com.google.common.base.MoreObjects;
import com.pigx.engine.assistant.access.constant.AccessConstants;
import me.zhyd.oauth.config.AuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;


@ConfigurationProperties(prefix = AccessConstants.PROPERTY_ACCESS_JUSTAUTH)
public class JustAuthProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;
    /**
     * State 缓存时长，默认5分钟
     */
    private Duration timeout = Duration.ofMinutes(5);
    /**
     * 第三方系统登录配置信息
     */
    private Map<String, AuthConfig> configs;
    /**
     * 支付宝登录 PublicKey
     */
    private String alipayPublicKey;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Map<String, AuthConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, AuthConfig> configs) {
        this.configs = configs;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("timeout", timeout)
                .add("alipayPublicKey", alipayPublicKey)
                .toString();
    }
}
