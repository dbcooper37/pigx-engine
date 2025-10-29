package com.pigx.engine.oss.dialect.core.exception;

import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;


public class OssBucketPolicyTooLargeException extends PlatformRuntimeException {

    public OssBucketPolicyTooLargeException() {
        super();
    }

    public OssBucketPolicyTooLargeException(String message) {
        super(message);
    }

    public OssBucketPolicyTooLargeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssBucketPolicyTooLargeException(Throwable cause) {
        super(cause);
    }

    protected OssBucketPolicyTooLargeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Feedback getFeedback() {
        return OssErrorCodes.OSS_BUCKET_POLICY_TOO_LARGE;
    }
}
