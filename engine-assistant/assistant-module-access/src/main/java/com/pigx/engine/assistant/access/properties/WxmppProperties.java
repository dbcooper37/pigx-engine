package com.pigx.engine.assistant.access.properties;

import com.pigx.engine.assistant.access.constant.AccessConstants;
import com.google.common.base.MoreObjects;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = AccessConstants.PROPERTY_ACCESS_WXMPP)
/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/properties/WxmppProperties.class */
public class WxmppProperties {
    private Boolean enabled;
    private boolean useRedis;
    private RedisConfig redis;
    private List<MpConfig> configs;

    public boolean isUseRedis() {
        return this.useRedis;
    }

    public void setUseRedis(boolean useRedis) {
        this.useRedis = useRedis;
    }

    public RedisConfig getRedis() {
        return this.redis;
    }

    public void setRedis(RedisConfig redis) {
        this.redis = redis;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/properties/WxmppProperties$RedisConfig.class */
    public static class RedisConfig {
        private String host;
        private Integer port;

        public String getHost() {
            return this.host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return this.port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("host", this.host).add("port", this.port).toString();
        }
    }

    public List<MpConfig> getConfigs() {
        return this.configs;
    }

    public void setConfigs(List<MpConfig> configs) {
        this.configs = configs;
    }

    /* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/properties/WxmppProperties$MpConfig.class */
    public static class MpConfig {
        private String appId;
        private String secret;
        private String token;
        private String aesKey;

        public String getAppId() {
            return this.appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getSecret() {
            return this.secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getToken() {
            return this.token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAesKey() {
            return this.aesKey;
        }

        public void setAesKey(String aesKey) {
            this.aesKey = aesKey;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).add("appId", this.appId).add("secret", this.secret).add("token", this.token).add("aesKey", this.aesKey).toString();
        }
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("useRedis", this.useRedis).add("redis", this.redis).add("configs", this.configs).toString();
    }
}
