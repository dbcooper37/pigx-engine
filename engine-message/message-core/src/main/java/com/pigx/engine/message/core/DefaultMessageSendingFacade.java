package com.pigx.engine.message.core;

import com.pigx.engine.message.core.constants.MessageConstants;


public class DefaultMessageSendingFacade extends MessageSendingFacade {

    /**
     * 发送 WebSocket 给指定用户
     *
     * @param user    用户唯一标识
     * @param payload 消息内容
     */
    public static void toUser(String user, Object payload) {
        pointToPoint(user, MessageConstants.WEBSOCKET_DESTINATION_PERSONAL_MESSAGE, payload);
    }

    /**
     * 发送公告信息
     *
     * @param payload 消息内容
     */
    public static void announcement(Object payload) {
        broadcast(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_NOTICE, payload);
    }
}
