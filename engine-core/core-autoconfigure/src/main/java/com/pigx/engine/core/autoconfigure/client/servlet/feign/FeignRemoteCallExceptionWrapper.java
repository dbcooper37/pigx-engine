package com.pigx.engine.autoconfigure.client.servlet.feign;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/servlet/feign/FeignRemoteCallExceptionWrapper.class */
public class FeignRemoteCallExceptionWrapper extends PlatformRuntimeException {
    private final Result<String> result;

    public FeignRemoteCallExceptionWrapper(Result<String> result) {
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(String message, Result<String> result) {
        super(message);
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(String message, Throwable cause, Result<String> result) {
        super(message, cause);
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(Throwable cause, Result<String> result) {
        super(cause);
        this.result = result;
    }

    public FeignRemoteCallExceptionWrapper(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Result<String> result) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.result = result;
    }

    @Override // com.pigx.engine.core.definition.exception.AbstractRuntimeException, com.pigx.engine.core.definition.exception.HerodotusException
    public Result<String> getResult() {
        return this.result;
    }
}
