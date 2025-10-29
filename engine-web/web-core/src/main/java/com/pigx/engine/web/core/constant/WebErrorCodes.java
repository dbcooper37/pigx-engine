package com.pigx.engine.web.core.constant;

import com.pigx.engine.core.definition.feedback.NotAcceptableFeedback;


public interface WebErrorCodes {

    NotAcceptableFeedback SESSION_INVALID = new NotAcceptableFeedback("Session已过期，请刷新再试");
    NotAcceptableFeedback REPEAT_SUBMISSION = new NotAcceptableFeedback("提交进行中，请不要重复提交");
    NotAcceptableFeedback FREQUENT_REQUESTS = new NotAcceptableFeedback("请求频繁，请稍后再试");
    NotAcceptableFeedback FEIGN_DECODER_IO_EXCEPTION = new NotAcceptableFeedback("Feign 解析 Fallback 错误信息出错");

}
