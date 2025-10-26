package com.pigx.engine.message.websocket.servlet.interceptor;

import com.pigx.engine.core.definition.constant.Jackson2CustomizerOrder;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.message.websocket.servlet.domain.WebSocketPrincipal;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/interceptor/WebSocketChannelInterceptor.class */
public class WebSocketChannelInterceptor implements ChannelInterceptor {
    private static final Logger log = LoggerFactory.getLogger(WebSocketChannelInterceptor.class);

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        WebSocketPrincipal principal = (WebSocketPrincipal) accessor.getUser();
        if (ObjectUtils.isEmpty(principal)) {
            log.warn("[Herodotus] |- WebSocket channel cannot fetch user principal.");
            return null;
        }
        StompCommand command = accessor.getCommand();
        if (ObjectUtils.isNotEmpty(command)) {
            switch (AnonymousClass1.$SwitchMap$org$springframework$messaging$simp$stomp$StompCommand[command.ordinal()]) {
                case Jackson2CustomizerOrder.CUSTOMIZER_DEFAULT /* 1 */:
                    List<String> tokenHeaders = accessor.getNativeHeader("Authorization");
                    String token = null;
                    if (CollectionUtils.isNotEmpty(tokenHeaders)) {
                        String temp = tokenHeaders.get(0);
                        if (StringUtils.isNotBlank(temp) && Strings.CS.startsWith(temp, SystemConstants.BEARER_TOKEN)) {
                            token = Strings.CI.removeStart(temp, SystemConstants.BEARER_TOKEN);
                        }
                    }
                    WebSocketPrincipal user = (WebSocketPrincipal) accessor.getUser();
                    log.debug("[Herodotus] |- Authentication user [{}] transmit token [{}] from frontend.", user.getName(), token);
                    break;
            }
        }
        return message;
    }

    /* renamed from: com.pigx.engine.message.websocket.servlet.interceptor.WebSocketChannelInterceptor$1, reason: invalid class name */
    /* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/interceptor/WebSocketChannelInterceptor$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$springframework$messaging$simp$stomp$StompCommand = new int[StompCommand.values().length];

        static {
            try {
                $SwitchMap$org$springframework$messaging$simp$stomp$StompCommand[StompCommand.CONNECT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$springframework$messaging$simp$stomp$StompCommand[StompCommand.DISCONNECT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    }

    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
    }

    public boolean preReceive(MessageChannel channel) {
        return true;
    }

    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return message;
    }

    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
    }
}
