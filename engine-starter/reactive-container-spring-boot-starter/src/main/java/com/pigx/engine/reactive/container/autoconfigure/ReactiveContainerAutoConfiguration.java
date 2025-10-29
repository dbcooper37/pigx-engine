package com.pigx.engine.reactive.container.autoconfigure;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;


@AutoConfiguration
public class ReactiveContainerAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ReactiveContainerAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Starter [Reactive Container] Configure.");
    }
}

