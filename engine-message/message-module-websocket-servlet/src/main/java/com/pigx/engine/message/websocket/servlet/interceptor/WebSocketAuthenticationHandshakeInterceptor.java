package com.pigx.engine.message.websocket.servlet.interceptor;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import com.pigx.engine.core.identity.oauth2.BearerTokenResolver;
import com.pigx.engine.message.websocket.servlet.utils.WebSocketUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/interceptor/WebSocketAuthenticationHandshakeInterceptor.class */
public class WebSocketAuthenticationHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger log = LoggerFactory.getLogger(WebSocketAuthenticationHandshakeInterceptor.class);
    private static final String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
    private final BearerTokenResolver bearerTokenResolver;

    public WebSocketAuthenticationHandshakeInterceptor(BearerTokenResolver bearerTokenResolver) {
        this.bearerTokenResolver = bearerTokenResolver;
    }

    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        HttpServletRequest httpServletRequest = WebSocketUtils.getHttpServletRequest(request);
        if (ObjectUtils.isNotEmpty(httpServletRequest)) {
            String protocol = httpServletRequest.getHeader(SEC_WEBSOCKET_PROTOCOL);
            String token = determineToken(protocol);
            if (StringUtils.isNotBlank(token)) {
                UserPrincipal details = this.bearerTokenResolver.resolve(token);
                attributes.put(SystemConstants.PRINCIPAL, details);
                log.debug("[Herodotus] |- WebSocket fetch the token is [{}].", token);
                return true;
            }
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            log.info("[Herodotus] |- Token is invalid for WebSocket, stop handshake.");
            return false;
        }
        return true;
    }

    private String determineToken(String protocol) {
        if (Strings.CS.contains(protocol, SymbolConstants.COMMA)) {
            String[] protocols = StringUtils.split(protocol, SymbolConstants.COMMA);
            for (String item : protocols) {
                if (!Strings.CS.endsWith(item, ".stomp")) {
                    return item;
                }
            }
            return null;
        }
        return null;
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        HttpServletRequest httpServletRequest = WebSocketUtils.getHttpServletRequest(request);
        HttpServletResponse httpServletResponse = WebSocketUtils.getHttpServletResponse(response);
        if (ObjectUtils.isNotEmpty(httpServletRequest) && ObjectUtils.isNotEmpty(httpServletResponse)) {
            httpServletResponse.setHeader(SEC_WEBSOCKET_PROTOCOL, "v10.stomp");
        }
        log.info("[Herodotus] |- WebSocket handshake success!");
    }
}
