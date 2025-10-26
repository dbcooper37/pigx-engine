package com.pigx.engine.message.core.constants;

import com.pigx.engine.core.definition.feedback.InternalServerErrorFeedback;
import com.pigx.engine.core.definition.feedback.NotAcceptableFeedback;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/constants/MessageErrorCodes.class */
public interface MessageErrorCodes {
    public static final NotAcceptableFeedback ILLEGAL_CHANNEL = new NotAcceptableFeedback("WebSocket Channel 设置错误");
    public static final NotAcceptableFeedback PRINCIPAL_NOT_FOUND = new NotAcceptableFeedback("WebSocket 无法获取用户身份信息");
    public static final InternalServerErrorFeedback INTEGRATION_MESSAGE_EXCEPTION = new InternalServerErrorFeedback("基于 Spring Integration 的消息错误");
}
