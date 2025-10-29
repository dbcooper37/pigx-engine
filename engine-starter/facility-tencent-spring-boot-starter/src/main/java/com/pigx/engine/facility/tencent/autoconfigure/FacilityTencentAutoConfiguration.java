package com.pigx.engine.facility.tencent.autoconfigure;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;


@AutoConfiguration
public class FacilityTencentAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(FacilityTencentAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Starter [Facility Tencent] Configure.");
    }
}
