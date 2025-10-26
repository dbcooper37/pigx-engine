package com.pigx.engine.web.core.constant;

import com.pigx.engine.core.definition.feedback.NotAcceptableFeedback;

/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/constant/WebErrorCodes.class */
public interface WebErrorCodes {
    public static final NotAcceptableFeedback SESSION_INVALID = new NotAcceptableFeedback("Session已过期，请刷新再试");
    public static final NotAcceptableFeedback REPEAT_SUBMISSION = new NotAcceptableFeedback("提交进行中，请不要重复提交");
    public static final NotAcceptableFeedback FREQUENT_REQUESTS = new NotAcceptableFeedback("请求频繁，请稍后再试");
    public static final NotAcceptableFeedback FEIGN_DECODER_IO_EXCEPTION = new NotAcceptableFeedback("Feign 解析 Fallback 错误信息出错");
}
