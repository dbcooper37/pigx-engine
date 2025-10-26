package com.pigx.engine.autoconfigure;

import cn.hutool.v7.extra.spring.SpringUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({SpringUtil.class})
/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/ApplicationAutoConfiguration.class */
public class ApplicationAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ApplicationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Auto [Application] Configure.");
    }
}
