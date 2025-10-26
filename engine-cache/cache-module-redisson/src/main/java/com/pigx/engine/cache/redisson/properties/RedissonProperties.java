package com.pigx.engine.redisson.properties;

import com.pigx.engine.cache.core.constants.CacheConstants;
import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.enums.Protocol;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = CacheConstants.PROPERTY_REDIS_REDISSON)
/* loaded from: cache-module-redisson-3.5.7.0.jar:cn/herodotus/engine/cache/redisson/properties/RedissonProperties.class */
public class RedissonProperties {
    private Boolean enabled = false;
    private Mode mode = Mode.SINGLE;
    private Boolean useSslConnection = false;
    private String config;
    private SingleServerConfig singleServerConfig;
    private ClusterServersConfig clusterServersConfig;
    private SentinelServersConfig sentinelServersConfig;

    /* loaded from: cache-module-redisson-3.5.7.0.jar:cn/herodotus/engine/cache/redisson/properties/RedissonProperties$Mode.class */
    public enum Mode {
        SINGLE,
        SENTINEL,
        CLUSTER
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Boolean getUseSslConnection() {
        return this.useSslConnection;
    }

    public void setUseSslConnection(Boolean useSslConnection) {
        this.useSslConnection = useSslConnection;
    }

    public String getProtocol() {
        return getUseSslConnection().booleanValue() ? Protocol.REDISS.getFormat() : Protocol.REDIS.getFormat();
    }

    public String getConfig() {
        return this.config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public SingleServerConfig getSingleServerConfig() {
        return this.singleServerConfig;
    }

    public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    public ClusterServersConfig getClusterServersConfig() {
        return this.clusterServersConfig;
    }

    public void setClusterServersConfig(ClusterServersConfig clusterServersConfig) {
        this.clusterServersConfig = clusterServersConfig;
    }

    public SentinelServersConfig getSentinelServersConfig() {
        return this.sentinelServersConfig;
    }

    public void setSentinelServersConfig(SentinelServersConfig sentinelServersConfig) {
        this.sentinelServersConfig = sentinelServersConfig;
    }

    public boolean isExternalConfig() {
        return StringUtils.isNotBlank(getConfig());
    }

    public boolean isYamlConfig() {
        if (isExternalConfig()) {
            return Strings.CI.endsWith(getConfig(), SymbolConstants.SUFFIX_YAML);
        }
        return false;
    }

    public boolean isJsonConfig() {
        if (isExternalConfig()) {
            return Strings.CI.endsWith(getConfig(), SymbolConstants.SUFFIX_JSON);
        }
        return false;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add(BaseConstants.PROPERTY_NAME_ENABLED, this.enabled).add("mode", this.mode).add("config", this.config).toString();
    }
}
