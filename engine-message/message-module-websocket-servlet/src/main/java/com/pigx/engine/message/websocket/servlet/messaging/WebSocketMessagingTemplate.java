package com.pigx.engine.message.websocket.servlet.messaging;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;


public record WebSocketMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate,
                                         SimpUserRegistry simpUserRegistry) {

    /**
     * 发送 WebSocket 点对点消息。发送信息给指定用户
     *
     * @param user        用户唯一标识
     * @param destination 消息同奥
     * @param payload     消息内容
     */
    public void pointToPoint(String user, String destination, Object payload) {
        if (isUserExist(user)) {
            simpMessagingTemplate.convertAndSendToUser(user, destination, payload);
        }
    }

    /**
     * 发送 WebSocket 广播消息。发送全员信息
     *
     * @param destination 消息同奥
     * @param payload     消息内容
     */
    public void broadcast(String destination, Object payload) {
        simpMessagingTemplate.convertAndSend(destination, payload);
    }

    /**
     * 根据用户 ID 获取到对应的 WebSocket 用户
     *
     * @param userId 系统用户ID
     * @return WebSocket 用户 {@link SimpUser}
     */
    public SimpUser getUser(String userId) {
        return simpUserRegistry.getUser(userId);
    }

    /**
     * 判断 WebSocket用户是否存在。
     * <p>
     * 注意：只能查询到当前所在 WebSocket实例中的实时 WebSocket 用户信息。如果实时用户在不同的实例中，则查询不到。
     *
     * @param userId 用户ID
     * @return true 用户存在，false 用户不存在
     */
    public boolean isUserExist(String userId) {
        return ObjectUtils.isNotEmpty(getUser(userId));
    }
}
