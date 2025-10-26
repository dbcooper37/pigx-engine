package com.pigx.engine.message.websocket.servlet.domain;

import com.google.common.collect.ImmutableMap;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/domain/WebSocketChannel.class */
public enum WebSocketChannel {
    NOTICE("/notice", "个人通知");


    @Schema(name = "消息端点")
    private final String destination;

    @Schema(name = "说明")
    private final String description;
    private static final Map<String, WebSocketChannel> INDEX_MAP = new HashMap();
    private static final List<Map<String, Object>> JSON_STRUCT = new ArrayList();

    static {
        for (WebSocketChannel webSocketChannel : values()) {
            INDEX_MAP.put(webSocketChannel.name(), webSocketChannel);
            JSON_STRUCT.add(webSocketChannel.ordinal(), ImmutableMap.builder().put("value", Integer.valueOf(webSocketChannel.ordinal())).put("key", webSocketChannel.name()).put("text", webSocketChannel.getDescription()).build());
        }
    }

    WebSocketChannel(String destination, String description) {
        this.destination = destination;
        this.description = description;
    }

    public String getDestination() {
        return this.destination;
    }

    public String getDescription() {
        return this.description;
    }

    public static WebSocketChannel getWebSocketChannel(String code) {
        return INDEX_MAP.get(code);
    }

    public static List<Map<String, Object>> getToJsonStruct() {
        return JSON_STRUCT;
    }
}
