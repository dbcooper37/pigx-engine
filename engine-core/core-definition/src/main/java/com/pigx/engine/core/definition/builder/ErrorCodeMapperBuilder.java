package com.pigx.engine.core.definition.builder;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.ErrorCodeMapper;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.feedback.CustomizeFeedback;
import com.pigx.engine.core.definition.feedback.ForbiddenFeedback;
import com.pigx.engine.core.definition.feedback.InternalServerErrorFeedback;
import com.pigx.engine.core.definition.feedback.MethodNotAllowedFeedback;
import com.pigx.engine.core.definition.feedback.NotAcceptableFeedback;
import com.pigx.engine.core.definition.feedback.NotFoundFeedback;
import com.pigx.engine.core.definition.feedback.NotImplementedFeedback;
import com.pigx.engine.core.definition.feedback.PreconditionFailedFeedback;
import com.pigx.engine.core.definition.feedback.ServiceUnavailableFeedback;
import com.pigx.engine.core.definition.feedback.UnauthorizedFeedback;
import com.pigx.engine.core.definition.feedback.UnsupportedMediaTypeFeedback;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/builder/ErrorCodeMapperBuilder.class */
public class ErrorCodeMapperBuilder {
    private final Map<Feedback, Integer> unauthorizedConfigs = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder.1
        {
            put(ErrorCodes.UNAUTHORIZED, Integer.valueOf(ErrorCodes.UNAUTHORIZED.getSequence()));
        }
    };
    private final Map<Feedback, Integer> forbiddenConfigs = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder.2
        {
            put(ErrorCodes.FORBIDDEN, Integer.valueOf(ErrorCodes.FORBIDDEN.getSequence()));
        }
    };
    private final Map<Feedback, Integer> notFoundConfigs = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder.3
        {
            put(ErrorCodes.NOT_FOUND, Integer.valueOf(ErrorCodes.NOT_FOUND.getSequence()));
        }
    };
    private final Map<Feedback, Integer> methodNotAllowedConfigs = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder.4
        {
            put(ErrorCodes.METHOD_NOT_ALLOWED, Integer.valueOf(ErrorCodes.METHOD_NOT_ALLOWED.getSequence()));
        }
    };
    private final Map<Feedback, Integer> notAcceptableConfigs = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder.5
        {
            put(ErrorCodes.NOT_ACCEPTABLE, Integer.valueOf(ErrorCodes.NOT_ACCEPTABLE.getSequence()));
        }
    };
    private final Map<Feedback, Integer> preconditionFailedConfigs = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder.6
        {
            put(ErrorCodes.PRECONDITION_FAILED, Integer.valueOf(ErrorCodes.PRECONDITION_FAILED.getSequence()));
        }
    };
    private final Map<Feedback, Integer> unsupportedMediaTypeConfigs = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder.7
        {
            put(ErrorCodes.PRECONDITION_FAILED, Integer.valueOf(ErrorCodes.PRECONDITION_FAILED.getSequence()));
        }
    };
    private final Map<Feedback, Integer> internalServerErrorConfigs = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder.8
        {
            put(ErrorCodes.INTERNAL_SERVER_ERROR, Integer.valueOf(ErrorCodes.INTERNAL_SERVER_ERROR.getSequence()));
        }
    };
    private final Map<Feedback, Integer> notImplementedConfigs = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder.9
        {
            put(ErrorCodes.NOT_IMPLEMENTED, Integer.valueOf(ErrorCodes.NOT_IMPLEMENTED.getSequence()));
        }
    };
    private final Map<Feedback, Integer> serviceUnavailableConfigs = new LinkedHashMap<Feedback, Integer>() { // from class: com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder.10
        {
            put(ErrorCodes.SERVICE_UNAVAILABLE, Integer.valueOf(ErrorCodes.SERVICE_UNAVAILABLE.getSequence()));
            put(ErrorCodes.OPEN_API_REQUEST_FAILURE, Integer.valueOf(ErrorCodes.OPEN_API_REQUEST_FAILURE.getSequence()));
        }
    };
    private final Map<Integer, Map<Feedback, Integer>> customizeConfigs = new LinkedHashMap();

    private ErrorCodeMapperBuilder create(Map<Feedback, Integer> container, Feedback... items) {
        for (Feedback item : items) {
            container.put(item, Integer.valueOf(item.getSequence(container.size())));
        }
        return this;
    }

    public ErrorCodeMapperBuilder customize(CustomizeFeedback... items) {
        for (CustomizeFeedback customizeFeedback : items) {
            if (customizeFeedback.isCustom()) {
                Map<Feedback, Integer> config = this.customizeConfigs.get(Integer.valueOf(customizeFeedback.getCustom()));
                if (MapUtils.isEmpty(config)) {
                    config = new LinkedHashMap();
                }
                config.put(customizeFeedback, Integer.valueOf(customizeFeedback.getSequence(config.size())));
                this.customizeConfigs.put(Integer.valueOf(customizeFeedback.getCustom()), config);
            }
        }
        return this;
    }

    public ErrorCodeMapperBuilder unauthorized(UnauthorizedFeedback... items) {
        return create(this.unauthorizedConfigs, items);
    }

    public ErrorCodeMapperBuilder forbidden(ForbiddenFeedback... items) {
        return create(this.forbiddenConfigs, items);
    }

    public ErrorCodeMapperBuilder notFound(NotFoundFeedback... items) {
        return create(this.notFoundConfigs, items);
    }

    public ErrorCodeMapperBuilder methodNotAllowed(MethodNotAllowedFeedback... items) {
        return create(this.methodNotAllowedConfigs, items);
    }

    public ErrorCodeMapperBuilder notAcceptable(NotAcceptableFeedback... items) {
        return create(this.notAcceptableConfigs, items);
    }

    public ErrorCodeMapperBuilder preconditionFailed(PreconditionFailedFeedback... items) {
        return create(this.preconditionFailedConfigs, items);
    }

    public ErrorCodeMapperBuilder unsupportedMediaType(UnsupportedMediaTypeFeedback... items) {
        return create(this.unsupportedMediaTypeConfigs, items);
    }

    public ErrorCodeMapperBuilder internalServerError(InternalServerErrorFeedback... items) {
        return create(this.internalServerErrorConfigs, items);
    }

    public ErrorCodeMapperBuilder notImplemented(NotImplementedFeedback... items) {
        return create(this.notImplementedConfigs, items);
    }

    public ErrorCodeMapperBuilder serviceUnavailable(ServiceUnavailableFeedback... items) {
        return create(this.serviceUnavailableConfigs, items);
    }

    public ErrorCodeMapper build() {
        ErrorCodeMapper errorCodeMapper = ErrorCodeMapper.getInstance();
        errorCodeMapper.append(this.unauthorizedConfigs);
        errorCodeMapper.append(this.forbiddenConfigs);
        errorCodeMapper.append(this.methodNotAllowedConfigs);
        errorCodeMapper.append(this.notAcceptableConfigs);
        errorCodeMapper.append(this.preconditionFailedConfigs);
        errorCodeMapper.append(this.unsupportedMediaTypeConfigs);
        errorCodeMapper.append(this.internalServerErrorConfigs);
        errorCodeMapper.append(this.notImplementedConfigs);
        errorCodeMapper.append(this.serviceUnavailableConfigs);
        errorCodeMapper.append(this.notFoundConfigs);
        this.customizeConfigs.forEach((key, feedbacks) -> {
            errorCodeMapper.append(feedbacks);
        });
        return errorCodeMapper;
    }
}
