package com.pigx.engine.web.core.constant;

import com.pigx.engine.core.definition.feedback.NotAcceptableFeedback;
import com.pigx.engine.core.definition.feedback.InternalServerErrorFeedback;


public interface WebErrorCodes {

    NotAcceptableFeedback SESSION_INVALID = new NotAcceptableFeedback("Session已过期，请刷新再试");
    NotAcceptableFeedback REPEAT_SUBMISSION = new NotAcceptableFeedback("提交进行中，请不要重复提交");
    NotAcceptableFeedback FREQUENT_REQUESTS = new NotAcceptableFeedback("请求频繁，请稍后再试");
    NotAcceptableFeedback FEIGN_DECODER_IO_EXCEPTION = new NotAcceptableFeedback("Feign 解析 Fallback 错误信息出错");
    
    // Crypto related error codes
    InternalServerErrorFeedback CRYPTO_DECRYPT_FAILED = new InternalServerErrorFeedback("数据解密失败");
    InternalServerErrorFeedback CRYPTO_ENCRYPT_FAILED = new InternalServerErrorFeedback("数据加密失败");
    NotAcceptableFeedback CRYPTO_SESSION_EXPIRED = new NotAcceptableFeedback("加密会话已过期，请刷新页面后重试");
    NotAcceptableFeedback CRYPTO_JSON_DEPTH_EXCEEDED = new NotAcceptableFeedback("JSON嵌套层级超过限制");

}
