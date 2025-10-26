package com.pigx.engine.assistant.access.constant;

import com.pigx.engine.core.definition.feedback.PreconditionFailedFeedback;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/constant/AccessErrorCodes.class */
public interface AccessErrorCodes {
    public static final PreconditionFailedFeedback ACCESS_CONFIG_ERROR = new PreconditionFailedFeedback("Access 模块配置错误");
    public static final PreconditionFailedFeedback ACCESS_HANDLER_NOT_FOUND = new PreconditionFailedFeedback("Access 模块接入处理器未找到错误");
    public static final PreconditionFailedFeedback ACCESS_IDENTITY_VERIFICATION_FAILED = new PreconditionFailedFeedback("接入身份认证错误");
    public static final PreconditionFailedFeedback ACCESS_PRE_PROCESS_FAILED_EXCEPTION = new PreconditionFailedFeedback("接入预操作失败错误");
    public static final PreconditionFailedFeedback ILLEGAL_ACCESS_ARGUMENT = new PreconditionFailedFeedback("社交登录参数错误");
    public static final PreconditionFailedFeedback ILLEGAL_ACCESS_SOURCE = new PreconditionFailedFeedback("社交登录Source参数错误");
}
