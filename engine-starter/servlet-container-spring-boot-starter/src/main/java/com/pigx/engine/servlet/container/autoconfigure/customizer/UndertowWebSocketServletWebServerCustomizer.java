package com.pigx.engine.servlet.container.autoconfigure.customizer;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;


public class UndertowWebSocketServletWebServerCustomizer implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    private static final Logger log = LoggerFactory.getLogger(UndertowWebSocketServletWebServerCustomizer.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Undertow WebServer Factory Customizer] Configure.");
    }

    @Override
    public void customize(UndertowServletWebServerFactory factory) {

        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
            deploymentInfo.addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME, webSocketDeploymentInfo);
        });
    }
}
