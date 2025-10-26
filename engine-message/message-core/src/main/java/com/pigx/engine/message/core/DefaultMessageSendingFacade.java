package com.pigx.engine.message.core;

import com.pigx.engine.message.core.constants.MessageConstants;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/DefaultMessageSendingFacade.class */
public class DefaultMessageSendingFacade extends MessageSendingFacade {
    public static void toUser(String user, Object payload) {
        pointToPoint(user, MessageConstants.WEBSOCKET_DESTINATION_PERSONAL_MESSAGE, payload);
    }

    public static void announcement(Object payload) {
        broadcast(MessageConstants.WEBSOCKET_DESTINATION_BROADCAST_NOTICE, payload);
    }
}
