package com.pigx.engine.redisson.config;

import com.pigx.engine.cache.redisson.annotation.ConditionalOnRedisson;
import com.pigx.engine.cache.redisson.properties.RedissonProperties;
import com.pigx.engine.core.definition.constant.Jackson2CustomizerOrder;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.foundation.utils.ResourceResolverUtils;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnRedisson
@EnableConfigurationProperties({RedissonProperties.class})
@Configuration(proxyBeanMethods = false)
/* loaded from: cache-module-redisson-3.5.7.0.jar:cn/herodotus/engine/cache/redisson/config/CacheRedissonConfiguration.class */
public class CacheRedissonConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CacheRedissonConfiguration.class);
    private final RedissonProperties redissonProperties;
    private final RedisProperties redisProperties;

    public CacheRedissonConfiguration(RedissonProperties redissonProperties, RedisProperties redisProperties) {
        this.redissonProperties = redissonProperties;
        this.redisProperties = redisProperties;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Cache Redisson] Configure.");
    }

    private File readConfigFile() {
        String configFile = this.redissonProperties.getConfig();
        if (StringUtils.isNotBlank(configFile)) {
            return ResourceResolverUtils.getFile(configFile);
        }
        return null;
    }

    private Config getConfigByFile() {
        try {
            File configFile = readConfigFile();
            if (ObjectUtils.isNotEmpty(configFile) && Strings.CI.endsWith(configFile.getName(), SymbolConstants.SUFFIX_YAML)) {
                return Config.fromYAML(configFile);
            }
            return null;
        } catch (IOException e) {
            log.error("[Herodotus] |- Redisson loading the config file error!");
            return null;
        }
    }

    private Config getDefaultConfig() {
        Config config = new Config();
        switch (AnonymousClass1.$SwitchMap$cn$herodotus$engine$cache$redisson$properties$RedissonProperties$Mode[this.redissonProperties.getMode().ordinal()]) {
            case Jackson2CustomizerOrder.CUSTOMIZER_DEFAULT /* 1 */:
                ClusterServersConfig clusterServersConfig = config.useClusterServers();
                ClusterServersConfig redissonClusterConfig = this.redissonProperties.getClusterServersConfig();
                if (ObjectUtils.isNotEmpty(redissonClusterConfig)) {
                    BeanUtils.copyProperties(redissonClusterConfig, clusterServersConfig, ClusterServersConfig.class);
                    clusterServersConfig.setNodeAddresses(redissonClusterConfig.getNodeAddresses());
                }
                if (CollectionUtils.isEmpty(clusterServersConfig.getNodeAddresses())) {
                    List<String> nodes = this.redisProperties.getCluster().getNodes();
                    Stream map = nodes.stream().map(a -> {
                        return this.redissonProperties.getProtocol() + a;
                    });
                    Objects.requireNonNull(clusterServersConfig);
                    map.forEach(xva$0 -> {
                        clusterServersConfig.addNodeAddress(new String[]{xva$0});
                    });
                }
                if (StringUtils.isBlank(clusterServersConfig.getPassword()) && StringUtils.isNotBlank(this.redisProperties.getPassword())) {
                    clusterServersConfig.setPassword(this.redisProperties.getPassword());
                    break;
                }
                break;
            case Jackson2CustomizerOrder.CUSTOMIZER_XSS /* 2 */:
                SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
                SentinelServersConfig redissonSentinelConfig = this.redissonProperties.getSentinelServersConfig();
                if (ObjectUtils.isNotEmpty(redissonSentinelConfig)) {
                    BeanUtils.copyProperties(redissonSentinelConfig, sentinelServersConfig, SentinelServersConfig.class);
                    sentinelServersConfig.setSentinelAddresses(redissonSentinelConfig.getSentinelAddresses());
                }
                if (CollectionUtils.isEmpty(sentinelServersConfig.getSentinelAddresses())) {
                    List<String> nodes2 = this.redisProperties.getSentinel().getNodes();
                    Stream map2 = nodes2.stream().map(a2 -> {
                        return this.redissonProperties.getProtocol() + a2;
                    });
                    Objects.requireNonNull(sentinelServersConfig);
                    map2.forEach(xva$02 -> {
                        sentinelServersConfig.addSentinelAddress(new String[]{xva$02});
                    });
                }
                if (StringUtils.isBlank(sentinelServersConfig.getPassword()) && StringUtils.isNotBlank(this.redisProperties.getPassword())) {
                    sentinelServersConfig.setPassword(this.redisProperties.getPassword());
                }
                if (StringUtils.isBlank(sentinelServersConfig.getMasterName())) {
                    sentinelServersConfig.setMasterName(this.redisProperties.getSentinel().getMaster());
                    break;
                }
                break;
            default:
                SingleServerConfig singleServerConfig = config.useSingleServer();
                if (ObjectUtils.isNotEmpty(this.redissonProperties.getSingleServerConfig())) {
                    BeanUtils.copyProperties(this.redissonProperties.getSingleServerConfig(), singleServerConfig, SingleServerConfig.class);
                }
                if (StringUtils.isBlank(singleServerConfig.getAddress())) {
                    singleServerConfig.setAddress(this.redissonProperties.getProtocol() + this.redisProperties.getHost() + ":" + this.redisProperties.getPort());
                }
                if (StringUtils.isBlank(singleServerConfig.getPassword()) && StringUtils.isNotBlank(this.redisProperties.getPassword())) {
                    singleServerConfig.setPassword(this.redisProperties.getPassword());
                }
                singleServerConfig.setDatabase(this.redisProperties.getDatabase());
                break;
        }
        config.setCodec(new JsonJacksonCodec());
        config.setLockWatchdogTimeout(30000L);
        return config;
    }

    /* renamed from: com.pigx.engine.cache.redisson.config.CacheRedissonConfiguration$1, reason: invalid class name */
    /* loaded from: cache-module-redisson-3.5.7.0.jar:cn/herodotus/engine/cache/redisson/config/CacheRedissonConfiguration$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$herodotus$engine$cache$redisson$properties$RedissonProperties$Mode = new int[RedissonProperties.Mode.values().length];

        static {
            try {
                $SwitchMap$cn$herodotus$engine$cache$redisson$properties$RedissonProperties$Mode[RedissonProperties.Mode.CLUSTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$cn$herodotus$engine$cache$redisson$properties$RedissonProperties$Mode[RedissonProperties.Mode.SENTINEL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = getConfigByFile();
        if (ObjectUtils.isEmpty(config)) {
            config = getDefaultConfig();
        }
        RedissonClient redissonClient = Redisson.create(config);
        log.trace("[Herodotus] |- Bean [Redisson Client] Configure.");
        return redissonClient;
    }
}
