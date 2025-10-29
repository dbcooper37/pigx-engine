package com.pigx.engine.core.autoconfigure.error;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.exception.PlatformRuntimeException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;


@Component
public class HerodotusGlobalErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     *
     * @param errorAttributes    the error attributes
     * @param resources          the resources configuration properties
     * @param errorProperties    the error configuration properties
     * @param applicationContext the current application context
     * @since 2.4.0
     */
    public HerodotusGlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Map<String, Object> error = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        int status = getHttpStatus(error);

        String path = getError(error, "path");
        String message = getError(error, "message");

        Throwable throwable = getError(request);
        if (throwable instanceof PlatformRuntimeException exception) {
            Feedback feedback = exception.getFeedback();
            Result<String> result = Result.failure(feedback);
            result.detail(message);
            result.path(path);
            result.stackTrace(exception.getStackTrace());
            status = result.getStatus();
            error = result.toModel();
        }

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(error));
    }

    private String getError(Map<String, Object> error, String key) {
        if (MapUtils.isNotEmpty(error)) {
            Object value = error.get(key);
            if (ObjectUtils.isNotEmpty(value)) {
                return String.valueOf(value);
            }
        }

        return SymbolConstants.BLANK;
    }
}
