package com.pigx.engine.data.rdbms.autoconfigure;

import com.pigx.engine.data.tenant.config.DataTenantConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@AutoConfiguration
@Import({
        DataTenantConfiguration.class,
})
@EnableJpaAuditing
public class DataJpaAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DataJpaAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Starter [Data Jpa] Configure.");
    }
}