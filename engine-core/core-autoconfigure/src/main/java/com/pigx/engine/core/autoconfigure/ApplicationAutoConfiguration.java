package com.pigx.engine.core.autoconfigure;

import cn.hutool.v7.extra.spring.SpringUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;


@AutoConfiguration
@Import({
        SpringUtil.class,
})
public class ApplicationAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ApplicationAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Application] Configure.");
    }
}
