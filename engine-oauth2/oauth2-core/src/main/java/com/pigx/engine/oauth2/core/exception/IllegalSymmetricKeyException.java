package com.pigx.engine.oauth2.core.exception;


import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.identity.exception.PlatformAuthenticationException;

/**
 * <p> Description : 非法加密Key HerodotusException </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/28 17:32
 */
public class IllegalSymmetricKeyException extends PlatformAuthenticationException {

    public IllegalSymmetricKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalSymmetricKeyException(String msg) {
        super(msg);
    }

    @Override
    public Feedback getFeedback() {
        return ErrorCodes.ILLEGAL_SYMMETRIC_KEY;
    }
}
