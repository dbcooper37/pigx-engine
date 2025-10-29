package com.pigx.engine.message.core.constants;

import com.pigx.engine.core.definition.feedback.InternalServerErrorFeedback;
import com.pigx.engine.core.definition.feedback.NotAcceptableFeedback;


public interface MessageErrorCodes {

    NotAcceptableFeedback ILLEGAL_CHANNEL = new NotAcceptableFeedback("WebSocket Channel 设置错误");
    NotAcceptableFeedback PRINCIPAL_NOT_FOUND = new NotAcceptableFeedback("WebSocket 无法获取用户身份信息");

    InternalServerErrorFeedback INTEGRATION_MESSAGE_EXCEPTION = new InternalServerErrorFeedback("基于 Spring Integration 的消息错误");
}
