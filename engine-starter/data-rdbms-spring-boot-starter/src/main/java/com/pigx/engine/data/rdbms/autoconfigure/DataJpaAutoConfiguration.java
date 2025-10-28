package com.pigx.engine.data.rdbms.autoconfigure;

import cn.herodotus.engine.data.tenant.config.DataTenantConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@AutoConfiguration
@Import({DataTenantConfiguration.class})
/* loaded from: data-rdbms-spring-boot-starter-3.5.7.0.jar:cn/herodotus/engine/data/rdbms/autoconfigure/DataJpaAutoConfiguration.class */
public class DataJpaAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DataJpaAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Starter [Data Jpa] Configure.");
    }
}
