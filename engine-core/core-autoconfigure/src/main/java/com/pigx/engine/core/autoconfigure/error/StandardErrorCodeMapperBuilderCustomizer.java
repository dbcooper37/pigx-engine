package com.pigx.engine.autoconfigure.error;

import com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder;
import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import org.springframework.core.Ordered;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/error/StandardErrorCodeMapperBuilderCustomizer.class */
public class StandardErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override // com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer
    public void customize(ErrorCodeMapperBuilder builder) {
        builder.unauthorized(ErrorCodes.ACCESS_DENIED, ErrorCodes.ACCOUNT_DISABLED, ErrorCodes.ACCOUNT_ENDPOINT_LIMITED, ErrorCodes.ACCOUNT_EXPIRED, ErrorCodes.ACCOUNT_LOCKED, ErrorCodes.BAD_CREDENTIALS, ErrorCodes.CREDENTIALS_EXPIRED, ErrorCodes.INVALID_CLIENT, ErrorCodes.INVALID_TOKEN, ErrorCodes.INVALID_GRANT, ErrorCodes.UNAUTHORIZED_CLIENT, ErrorCodes.USERNAME_NOT_FOUND, ErrorCodes.SESSION_EXPIRED, ErrorCodes.NOT_AUTHENTICATED).forbidden(ErrorCodes.INSUFFICIENT_SCOPE, ErrorCodes.SQL_INJECTION_REQUEST).methodNotAllowed(ErrorCodes.HTTP_REQUEST_METHOD_NOT_SUPPORTED).notAcceptable(ErrorCodes.UNSUPPORTED_GRANT_TYPE, ErrorCodes.UNSUPPORTED_RESPONSE_TYPE, ErrorCodes.UNSUPPORTED_TOKEN_TYPE, ErrorCodes.USERNAME_ALREADY_EXISTS, ErrorCodes.FEIGN_DECODER_IO_EXCEPTION, ErrorCodes.CAPTCHA_CATEGORY_IS_INCORRECT, ErrorCodes.CAPTCHA_HANDLER_NOT_EXIST, ErrorCodes.CAPTCHA_HAS_EXPIRED, ErrorCodes.CAPTCHA_IS_EMPTY, ErrorCodes.CAPTCHA_MISMATCH, ErrorCodes.CAPTCHA_PARAMETER_ILLEGAL).notFound(ErrorCodes.NOT_FOUND, ErrorCodes.NO_RESOURCE_FOUND_EXCEPTION).preconditionFailed(ErrorCodes.INVALID_REDIRECT_URI, ErrorCodes.INVALID_REQUEST, ErrorCodes.INVALID_SCOPE, ErrorCodes.METHOD_ARGUMENT_NOT_VALID).unsupportedMediaType(ErrorCodes.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE).internalServerError(ErrorCodes.SERVER_ERROR, ErrorCodes.HTTP_MESSAGE_NOT_READABLE_EXCEPTION, ErrorCodes.ILLEGAL_ARGUMENT_EXCEPTION, ErrorCodes.IO_EXCEPTION, ErrorCodes.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION, ErrorCodes.NULL_POINTER_EXCEPTION, ErrorCodes.TYPE_MISMATCH_EXCEPTION, ErrorCodes.BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION, ErrorCodes.OPENAPI_INVOKING_FAILED).notImplemented(ErrorCodes.PROPERTY_VALUE_IS_NOT_SET_EXCEPTION, ErrorCodes.URL_FORMAT_INCORRECT_EXCEPTION, ErrorCodes.ILLEGAL_SYMMETRIC_KEY, ErrorCodes.DISCOVERED_UNRECORDED_ERROR_EXCEPTION).serviceUnavailable(ErrorCodes.COOKIE_THEFT, ErrorCodes.INVALID_COOKIE, ErrorCodes.PROVIDER_NOT_FOUND, ErrorCodes.TEMPORARILY_UNAVAILABLE, ErrorCodes.SEARCH_IP_LOCATION).customize(ErrorCodes.TRANSACTION_ROLLBACK, ErrorCodes.BAD_SQL_GRAMMAR, ErrorCodes.DATA_INTEGRITY_VIOLATION, ErrorCodes.PIPELINE_INVALID_COMMANDS);
    }

    public int getOrder() {
        return 0;
    }
}
