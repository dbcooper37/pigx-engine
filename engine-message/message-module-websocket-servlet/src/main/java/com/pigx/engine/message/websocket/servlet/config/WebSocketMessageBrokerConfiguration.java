package com.pigx.engine.message.websocket.servlet.config;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.message.core.constants.MessageConstants;
import com.pigx.engine.message.websocket.servlet.interceptor.WebSocketAuthenticationHandshakeInterceptor;
import com.pigx.engine.message.websocket.servlet.interceptor.WebSocketChannelInterceptor;
import com.pigx.engine.message.websocket.servlet.interceptor.WebSocketPrincipalHandshakeHandler;
import com.pigx.engine.message.websocket.servlet.properties.WebSocketProperties;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeInterceptor;

@EnableConfigurationProperties({WebSocketProperties.class})
@EnableScheduling
@Configuration(proxyBeanMethods = false)
@EnableWebSocketMessageBroker
/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/config/WebSocketMessageBrokerConfiguration.class */
public class WebSocketMessageBrokerConfiguration extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {
    private static final Logger log = LoggerFactory.getLogger(WebSocketMessageBrokerConfiguration.class);
    private final WebSocketProperties webSocketProperties;
    private final WebSocketChannelInterceptor webSocketChannelInterceptor = new WebSocketChannelInterceptor();
    private final WebSocketPrincipalHandshakeHandler webSocketPrincipalHandshakeHandler = new WebSocketPrincipalHandshakeHandler();
    private final WebSocketAuthenticationHandshakeInterceptor webSocketAuthenticationHandshakeInterceptor;

    public WebSocketMessageBrokerConfiguration(WebSocketProperties webSocketProperties, WebSocketAuthenticationHandshakeInterceptor webSocketAuthenticationHandshakeInterceptor) {
        this.webSocketProperties = webSocketProperties;
        this.webSocketAuthenticationHandshakeInterceptor = webSocketAuthenticationHandshakeInterceptor;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Module [WebSocket Message Broker] Configure.");
    }

    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(new String[]{this.webSocketProperties.getEndpoint()}).setAllowedOrigins(new String[]{SymbolConstants.STAR}).addInterceptors(new HandshakeInterceptor[]{this.webSocketAuthenticationHandshakeInterceptor}).setHandshakeHandler(this.webSocketPrincipalHandshakeHandler).withSockJS();
        registry.addEndpoint(new String[]{this.webSocketProperties.getEndpoint()}).setAllowedOrigins(new String[]{SymbolConstants.STAR}).addInterceptors(new HandshakeInterceptor[]{this.webSocketAuthenticationHandshakeInterceptor}).setHandshakeHandler(this.webSocketPrincipalHandshakeHandler);
    }

    public void configureMessageBroker(MessageBrokerRegistry registry) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("herodotus-websocket-heartbeat-thread-");
        taskScheduler.initialize();
        registry.enableSimpleBroker(new String[]{MessageConstants.WEBSOCKET_CHANNEL_PROXY_BROADCAST, MessageConstants.WEBSOCKET_CHANNEL_PROXY_PERSONAL}).setHeartbeatValue(new long[]{10000, 10000}).setTaskScheduler(taskScheduler);
        String[] applicationDestinationPrefixes = this.webSocketProperties.getApplicationPrefixes();
        if (ArrayUtils.isNotEmpty(applicationDestinationPrefixes)) {
            registry.setApplicationDestinationPrefixes(applicationDestinationPrefixes);
        }
        if (StringUtils.isNotBlank(this.webSocketProperties.getUserDestinationPrefix())) {
            registry.setUserDestinationPrefix(this.webSocketProperties.getUserDestinationPrefix());
        }
    }

    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.setMessageSizeLimit(10240).setSendBufferSizeLimit(10240).setSendTimeLimit(10000);
        super.configureWebSocketTransport(registration);
    }

    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(10).maxPoolSize(20).keepAliveSeconds(60);
        registration.interceptors(new ChannelInterceptor[]{this.webSocketChannelInterceptor});
        super.configureClientInboundChannel(registration);
    }

    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(10).maxPoolSize(20).keepAliveSeconds(60);
    }
}
