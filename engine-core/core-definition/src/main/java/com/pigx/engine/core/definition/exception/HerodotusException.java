package com.pigx.engine.core.definition.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.domain.Result;


public interface HerodotusException {

    /**
     * 获取反馈信息
     *
     * @return 反馈信息对象 {@link Feedback}
     */
    Feedback getFeedback();

    /**
     * 错误信息转换为 Result 对象。
     *
     * @return 结果对象 {@link Result}
     */
    Result<String> getResult();
}
