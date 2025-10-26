package com.pigx.engine.message.websocket.servlet.config;

import com.pigx.engine.core.identity.oauth2.BearerTokenResolver;
import com.pigx.engine.message.core.domain.WebSocketMessage;
import com.pigx.engine.message.websocket.servlet.annotation.ConditionalOnMultipleWebSocketInstance;
import com.pigx.engine.message.websocket.servlet.annotation.ConditionalOnSingleWebSocketInstance;
import com.pigx.engine.message.websocket.servlet.definition.WebSocketMessageSender;
import com.pigx.engine.message.websocket.servlet.interceptor.WebSocketAuthenticationHandshakeInterceptor;
import com.pigx.engine.message.websocket.servlet.messaging.MultipleInstanceMessageSender;
import com.pigx.engine.message.websocket.servlet.messaging.MultipleInstanceMessageSyncConsumer;
import com.pigx.engine.message.websocket.servlet.messaging.SingleInstanceMessageSender;
import com.pigx.engine.message.websocket.servlet.messaging.WebSocketMessageSendingAdapter;
import com.pigx.engine.message.websocket.servlet.messaging.WebSocketMessagingTemplate;
import jakarta.annotation.PostConstruct;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;

@Configuration(proxyBeanMethods = false)
/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/config/MessageWebSocketConfiguration.class */
public class MessageWebSocketConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MessageWebSocketConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [Message WebSocket] Configure.");
    }

    @Bean
    public WebSocketAuthenticationHandshakeInterceptor webSocketPrincipalHandshakeHandler(BearerTokenResolver bearerTokenResolver) {
        WebSocketAuthenticationHandshakeInterceptor interceptor = new WebSocketAuthenticationHandshakeInterceptor(bearerTokenResolver);
        log.trace("[Herodotus] |- Bean [WebSocket Authentication Handshake Interceptor] Configure.");
        return interceptor;
    }

    @ConditionalOnMissingBean
    @Bean
    public WebSocketMessagingTemplate webSocketMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry) {
        WebSocketMessagingTemplate template = new WebSocketMessagingTemplate(simpMessagingTemplate, simpUserRegistry);
        log.trace("[Herodotus] |- Bean [WebSocket Messaging Template] Configure.");
        return template;
    }

    @ConditionalOnSingleWebSocketInstance
    @Configuration(proxyBeanMethods = false)
    /* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/config/MessageWebSocketConfiguration$SingleInstanceConfiguration.class */
    static class SingleInstanceConfiguration {
        SingleInstanceConfiguration() {
        }

        @ConditionalOnMissingBean
        @Bean
        public WebSocketMessageSender singleInstanceMessageSender(WebSocketMessagingTemplate webSocketMessagingTemplate) {
            SingleInstanceMessageSender sender = new SingleInstanceMessageSender(webSocketMessagingTemplate);
            MessageWebSocketConfiguration.log.debug("[Herodotus] |- Strategy [Single Instance Web Socket Message Sender] Configure.");
            return sender;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMultipleWebSocketInstance
    /* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/config/MessageWebSocketConfiguration$MultipleInstanceConfiguration.class */
    static class MultipleInstanceConfiguration {
        MultipleInstanceConfiguration() {
        }

        @ConditionalOnMissingBean
        @Bean
        public WebSocketMessageSender multipleInstanceMessageSender(WebSocketMessagingTemplate webSocketMessagingTemplate) {
            MultipleInstanceMessageSender sender = new MultipleInstanceMessageSender(webSocketMessagingTemplate);
            MessageWebSocketConfiguration.log.debug("[Herodotus] |- Strategy [Multiple Instance Web Socket Message Sender] Configure.");
            return sender;
        }

        @Bean
        public Consumer<WebSocketMessage> webSocketConsumer(WebSocketMessagingTemplate webSocketMessagingTemplate) {
            MultipleInstanceMessageSyncConsumer consumer = new MultipleInstanceMessageSyncConsumer(webSocketMessagingTemplate);
            MessageWebSocketConfiguration.log.trace("[Herodotus] |- Bean [Multiple Instance Message Receiver] Configure.");
            return consumer;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @Import({WebSocketMessageBrokerConfiguration.class})
    @ComponentScan(basePackages = {"com.pigx.engine.message.websocket.servlet.controller", "com.pigx.engine.message.websocket.servlet.listener"})
    /* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/config/MessageWebSocketConfiguration$WebSocketConfiguration.class */
    static class WebSocketConfiguration {
        WebSocketConfiguration() {
        }

        @Bean
        public WebSocketMessageSendingAdapter webSocketMessageSendingAdapter(WebSocketMessageSender webSocketMessageSender) {
            WebSocketMessageSendingAdapter adapter = new WebSocketMessageSendingAdapter(webSocketMessageSender);
            MessageWebSocketConfiguration.log.trace("[Herodotus] |- Bean [WebSocket Message Sending Adapter] Configure.");
            return adapter;
        }
    }
}
