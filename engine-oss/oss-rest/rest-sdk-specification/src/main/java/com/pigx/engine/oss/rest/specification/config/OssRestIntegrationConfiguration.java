package com.pigx.engine.oss.rest.specification.config;

import com.pigx.engine.oss.solution.config.OssSolutionConfiguration;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@AutoConfiguration
@Import({
        OssSolutionConfiguration.class
})
@ComponentScan(basePackages = {
        "cn.herodotus.oss.rest.specification.controller",
})
public class OssRestIntegrationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(OssRestIntegrationConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Oss Rest Integration] Configure.");
    }
}
