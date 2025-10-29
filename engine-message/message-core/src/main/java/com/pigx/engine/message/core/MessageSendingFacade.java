package com.pigx.engine.message.core;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.message.core.domain.StreamMessage;
import com.pigx.engine.message.core.domain.TemplateMessage;
import com.pigx.engine.message.core.domain.WebSocketMessage;
import com.pigx.engine.message.core.event.StreamMessageSendingEvent;
import com.pigx.engine.message.core.event.TemplateMessageSendingEvent;
import org.apache.commons.lang3.StringUtils;


class MessageSendingFacade {

    /**
     * 发送消息
     *
     * @param event {@link AbstractApplicationEvent}
     */
    private static <T> void postProcess(AbstractApplicationEvent<T> event) {
        ServiceContextHolder.publishEvent(event);
    }

    /**
     * 发送事件类型消息
     *
     * @param event 消息
     * @param <T>   消息实体
     */
    public static <T> void event(AbstractApplicationEvent<T> event) {
        postProcess(event);
    }

    /**
     * 发送 MessageTemplate 类型消息
     *
     * @param message 消息
     * @param <T>     继承 {@link TemplateMessage} 类型消息实体
     */
    public static <T extends TemplateMessage> void template(T message) {
        postProcess(new TemplateMessageSendingEvent<>(message));
    }

    /**
     * 发送 Spring Cloud Stream 类型消息
     *
     * @param message 消息
     * @param <T>     继承 {@link StreamMessage} 类型消息实体
     */
    public static <T extends StreamMessage> void stream(T message) {
        postProcess(new StreamMessageSendingEvent(message));
    }

    /**
     * 发送 WebSocket 点对点消息
     *
     * @param user        用户唯一标识
     * @param destination 消息同奥
     * @param payload     消息内容
     * @param <T>         消息内容类型
     */
    public static <T> void pointToPoint(String user, String destination, T payload) {
        WebSocketMessage message = new WebSocketMessage();
        if (StringUtils.isNotBlank(user)) {
            message.setUser(user);
        }
        message.setDestination(destination);
        message.setPayload(payload);
        template(message);
    }

    /**
     * 发送 WebSocket 广播消息
     *
     * @param destination 消息同奥
     * @param payload     消息内容
     * @param <T>         消息内容类型
     */
    public static <T> void broadcast(String destination, T payload) {
        pointToPoint(null, destination, payload);
    }
}
