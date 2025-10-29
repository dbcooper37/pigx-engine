package com.pigx.engine.message.websocket.servlet.interceptor;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.constant.SystemConstants;
import com.pigx.engine.core.identity.domain.UserPrincipal;
import com.pigx.engine.core.identity.oauth2.BearerTokenResolver;
import com.pigx.engine.message.websocket.servlet.utils.WebSocketUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import java.util.Map;


public class WebSocketAuthenticationHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger log = LoggerFactory.getLogger(WebSocketAuthenticationHandshakeInterceptor.class);

    private static final String SEC_WEBSOCKET_PROTOCOL = com.google.common.net.HttpHeaders.SEC_WEBSOCKET_PROTOCOL;

    private final BearerTokenResolver bearerTokenResolver;

    public WebSocketAuthenticationHandshakeInterceptor(BearerTokenResolver bearerTokenResolver) {
        this.bearerTokenResolver = bearerTokenResolver;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        HttpServletRequest httpServletRequest = WebSocketUtils.getHttpServletRequest(request);

        if (ObjectUtils.isNotEmpty(httpServletRequest)) {

            String protocol = httpServletRequest.getHeader(SEC_WEBSOCKET_PROTOCOL);

            String token = determineToken(protocol);

            if (StringUtils.isNotBlank(token)) {
                UserPrincipal details = bearerTokenResolver.resolve(token);
                attributes.put(SystemConstants.PRINCIPAL, details);
                log.debug("[PIGXD] |- WebSocket fetch the token is [{}].", token);
            } else {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                log.info("[PIGXD] |- Token is invalid for WebSocket, stop handshake.");
                return false;
            }
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
        }
        return null;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {

        HttpServletRequest httpServletRequest = WebSocketUtils.getHttpServletRequest(request);
        HttpServletResponse httpServletResponse = WebSocketUtils.getHttpServletResponse(response);

        if (ObjectUtils.isNotEmpty(httpServletRequest) && ObjectUtils.isNotEmpty(httpServletResponse)) {
            httpServletResponse.setHeader(SEC_WEBSOCKET_PROTOCOL, "v10.stomp");
        }

        log.info("[PIGXD] |- WebSocket handshake success!");
    }
}
