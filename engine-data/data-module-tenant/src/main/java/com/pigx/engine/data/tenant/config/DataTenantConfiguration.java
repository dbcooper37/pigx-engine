package com.pigx.engine.data.tenant.config;

import com.pigx.engine.data.tenant.metrics.DataSourceMetricsExporter;
import com.pigx.engine.data.tenant.properties.MultiTenantProperties;
import com.pigx.engine.data.tenant.service.MultiTenantDataSourceFactory;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.micrometer.core.instrument.MeterRegistry;


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MultiTenantProperties.class)
@Import({
        DiscriminatorApproachConfiguration.class,
        SchemaApproachConfiguration.class,
        DatabaseApproachConfiguration.class,
})
public class DataTenantConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DataTenantConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Tenant] Configure.");
    }

    /**
     * Configuration for connection pool metrics when Micrometer is available.
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(MeterRegistry.class)
    @EnableScheduling
    static class DataSourceMetricsConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public DataSourceMetricsExporter dataSourceMetricsExporter(MultiTenantDataSourceFactory dataSourceFactory) {
            DataSourceMetricsExporter exporter = new DataSourceMetricsExporter(dataSourceFactory);
            log.trace("[PIGXD] |- Bean [DataSource Metrics Exporter] Configure.");
            return exporter;
        }
    }
}
