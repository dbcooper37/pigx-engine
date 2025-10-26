package com.pigx.engine.message.websocket.servlet.messaging;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;

/* loaded from: message-module-websocket-servlet-3.5.7.0.jar:cn/herodotus/engine/message/websocket/servlet/messaging/WebSocketMessagingTemplate.class */
public final class WebSocketMessagingTemplate extends Record {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SimpUserRegistry simpUserRegistry;

    public WebSocketMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WebSocketMessagingTemplate.class), WebSocketMessagingTemplate.class, "simpMessagingTemplate;simpUserRegistry", "FIELD:Lcn/herodotus/engine/message/websocket/servlet/messaging/WebSocketMessagingTemplate;->simpMessagingTemplate:Lorg/springframework/messaging/simp/SimpMessagingTemplate;", "FIELD:Lcn/herodotus/engine/message/websocket/servlet/messaging/WebSocketMessagingTemplate;->simpUserRegistry:Lorg/springframework/messaging/simp/user/SimpUserRegistry;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WebSocketMessagingTemplate.class), WebSocketMessagingTemplate.class, "simpMessagingTemplate;simpUserRegistry", "FIELD:Lcn/herodotus/engine/message/websocket/servlet/messaging/WebSocketMessagingTemplate;->simpMessagingTemplate:Lorg/springframework/messaging/simp/SimpMessagingTemplate;", "FIELD:Lcn/herodotus/engine/message/websocket/servlet/messaging/WebSocketMessagingTemplate;->simpUserRegistry:Lorg/springframework/messaging/simp/user/SimpUserRegistry;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WebSocketMessagingTemplate.class, Object.class), WebSocketMessagingTemplate.class, "simpMessagingTemplate;simpUserRegistry", "FIELD:Lcn/herodotus/engine/message/websocket/servlet/messaging/WebSocketMessagingTemplate;->simpMessagingTemplate:Lorg/springframework/messaging/simp/SimpMessagingTemplate;", "FIELD:Lcn/herodotus/engine/message/websocket/servlet/messaging/WebSocketMessagingTemplate;->simpUserRegistry:Lorg/springframework/messaging/simp/user/SimpUserRegistry;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public SimpMessagingTemplate simpMessagingTemplate() {
        return this.simpMessagingTemplate;
    }

    public SimpUserRegistry simpUserRegistry() {
        return this.simpUserRegistry;
    }

    public void pointToPoint(String user, String destination, Object payload) {
        if (isUserExist(user)) {
            this.simpMessagingTemplate.convertAndSendToUser(user, destination, payload);
        }
    }

    public void broadcast(String destination, Object payload) {
        this.simpMessagingTemplate.convertAndSend(destination, payload);
    }

    public SimpUser getUser(String userId) {
        return this.simpUserRegistry.getUser(userId);
    }

    public boolean isUserExist(String userId) {
        return ObjectUtils.isNotEmpty(getUser(userId));
    }
}
