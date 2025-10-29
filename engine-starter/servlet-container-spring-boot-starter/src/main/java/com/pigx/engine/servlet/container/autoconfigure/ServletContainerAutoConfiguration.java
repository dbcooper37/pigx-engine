package com.pigx.engine.servlet.container.autoconfigure;

import com.pigx.engine.core.foundation.condition.ConditionalOnServletApplication;
import com.pigx.engine.servlet.container.autoconfigure.customizer.UndertowWebSocketServletWebServerCustomizer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@AutoConfiguration(before = WebSocketServletAutoConfiguration.class)
public class ServletContainerAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ServletContainerAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Starter [Servlet Container] Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(io.undertow.websockets.jsr.Bootstrap.class)
    @ConditionalOnServletApplication
    static class UndertowWebSocketConfiguration {

        @Bean
        public UndertowWebSocketServletWebServerCustomizer websocketServletWebServerCustomizer() {
            UndertowWebSocketServletWebServerCustomizer customizer = new UndertowWebSocketServletWebServerCustomizer();
            log.trace("[PIGXD] |- Undertow websocket servlet web server customizer Configure.");
            return customizer;
        }
    }
}
