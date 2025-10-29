package com.pigx.engine.message.websocket.servlet.config;

import com.pigx.engine.core.identity.oauth2.BearerTokenResolver;
import com.pigx.engine.message.core.domain.WebSocketMessage;
import com.pigx.engine.message.websocket.servlet.annotation.ConditionalOnMultipleWebSocketInstance;
import com.pigx.engine.message.websocket.servlet.annotation.ConditionalOnSingleWebSocketInstance;
import com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender;
import com.pigx.engine.message.websocket.servlet.interceptor.WebSocketAuthenticationHandshakeInterceptor;
import com.pigx.engine.message.websocket.servlet.messaging.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;

import java.util.function.Consumer;


@Configuration(proxyBeanMethods = false)
public class MessageWebSocketConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MessageWebSocketConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[PIGXD] |- Module [Message WebSocket] Configure.");
    }

    @Bean
    public WebSocketAuthenticationHandshakeInterceptor webSocketPrincipalHandshakeHandler(BearerTokenResolver bearerTokenResolver) {
        WebSocketAuthenticationHandshakeInterceptor interceptor = new WebSocketAuthenticationHandshakeInterceptor(bearerTokenResolver);
        log.trace("[PIGXD] |- Bean [WebSocket Authentication Handshake Interceptor] Configure.");
        return interceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSocketMessagingTemplate webSocketMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry) {
        WebSocketMessagingTemplate template = new WebSocketMessagingTemplate(simpMessagingTemplate, simpUserRegistry);
        log.trace("[PIGXD] |- Bean [WebSocket Messaging Template] Configure.");
        return template;
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnSingleWebSocketInstance
    static class SingleInstanceConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public WebSocketMessageSender singleInstanceMessageSender(WebSocketMessagingTemplate webSocketMessagingTemplate) {
            SingleInstanceMessageSender sender = new SingleInstanceMessageSender(webSocketMessagingTemplate);
            log.debug("[PIGXD] |- Strategy [Single Instance Web Socket Message Sender] Configure.");
            return sender;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMultipleWebSocketInstance
    static class MultipleInstanceConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public WebSocketMessageSender multipleInstanceMessageSender(WebSocketMessagingTemplate webSocketMessagingTemplate) {
            MultipleInstanceMessageSender sender = new MultipleInstanceMessageSender(webSocketMessagingTemplate);
            log.debug("[PIGXD] |- Strategy [Multiple Instance Web Socket Message Sender] Configure.");
            return sender;
        }

        @Bean
        public Consumer<WebSocketMessage> webSocketConsumer(WebSocketMessagingTemplate webSocketMessagingTemplate) {
            MultipleInstanceMessageSyncConsumer consumer = new MultipleInstanceMessageSyncConsumer(webSocketMessagingTemplate);
            log.trace("[PIGXD] |- Bean [Multiple Instance Message Receiver] Configure.");
            return consumer;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @Import({
            WebSocketMessageBrokerConfiguration.class,
    })
    @ComponentScan(basePackages = {
            "com.pigx.engine.message.websocket.servlet.controller",
            "com.pigx.engine.message.websocket.servlet.listener",
    })
    static class WebSocketConfiguration {

        @Bean
        public WebSocketMessageSendingAdapter webSocketMessageSendingAdapter(WebSocketMessageSender webSocketMessageSender) {
            WebSocketMessageSendingAdapter adapter = new WebSocketMessageSendingAdapter(webSocketMessageSender);
            log.trace("[PIGXD] |- Bean [WebSocket Message Sending Adapter] Configure.");
            return adapter;
        }
    }
}
