package com.pigx.engine.core.definition.builder;

import com.pigx.engine.core.definition.constant.ErrorCodes;
import com.pigx.engine.core.definition.domain.ErrorCodeMapper;
import com.pigx.engine.core.definition.domain.Feedback;
import com.pigx.engine.core.definition.feedback.*;
import org.apache.commons.collections4.MapUtils;

import java.util.LinkedHashMap;
import java.util.Map;


public class ErrorCodeMapperBuilder {

    /**
     * 401	Unauthorized	请求要求用户的身份认证
     */
    private final Map<Feedback, Integer> unauthorizedConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.UNAUTHORIZED, ErrorCodes.UNAUTHORIZED.getSequence());
    }};
    /**
     * 403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
     */
    private final Map<Feedback, Integer> forbiddenConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.FORBIDDEN, ErrorCodes.FORBIDDEN.getSequence());
    }};
    /**
     * 404	Not Found	服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面
     */
    private final Map<Feedback, Integer> notFoundConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.NOT_FOUND, ErrorCodes.NOT_FOUND.getSequence());
    }};
    /**
     * 405	Method Not Allowed	客户端请求中的方法被禁止
     */
    private final Map<Feedback, Integer> methodNotAllowedConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.METHOD_NOT_ALLOWED, ErrorCodes.METHOD_NOT_ALLOWED.getSequence());
    }};
    /**
     * 406	Not Acceptable	服务器无法根据客户端请求的内容特性完成请求
     */
    private final Map<Feedback, Integer> notAcceptableConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.NOT_ACCEPTABLE, ErrorCodes.NOT_ACCEPTABLE.getSequence());
    }};
    /**
     * 412	Precondition Failed	客户端请求信息的先决条件错误
     */
    private final Map<Feedback, Integer> preconditionFailedConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.PRECONDITION_FAILED, ErrorCodes.PRECONDITION_FAILED.getSequence());
    }};
    /**
     * 415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
     */
    private final Map<Feedback, Integer> unsupportedMediaTypeConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.PRECONDITION_FAILED, ErrorCodes.PRECONDITION_FAILED.getSequence());
    }};
    /**
     * 500	Internal Server Error	服务器内部错误，无法完成请求
     */
    private final Map<Feedback, Integer> internalServerErrorConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.INTERNAL_SERVER_ERROR, ErrorCodes.INTERNAL_SERVER_ERROR.getSequence());
    }};
    /**
     * 501	Not Implemented	服务器不支持请求的功能，无法完成请求
     */
    private final Map<Feedback, Integer> notImplementedConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.NOT_IMPLEMENTED, ErrorCodes.NOT_IMPLEMENTED.getSequence());
    }};
    /**
     * 503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     */
    private final Map<Feedback, Integer> serviceUnavailableConfigs = new LinkedHashMap<>() {{
        put(ErrorCodes.SERVICE_UNAVAILABLE, ErrorCodes.SERVICE_UNAVAILABLE.getSequence());
        put(ErrorCodes.OPEN_API_REQUEST_FAILURE, ErrorCodes.OPEN_API_REQUEST_FAILURE.getSequence());
    }};

    private final Map<Integer, Map<Feedback, Integer>> customizeConfigs = new LinkedHashMap<>();

    private ErrorCodeMapperBuilder create(Map<Feedback, Integer> container, Feedback... items) {
        for (Feedback item : items) {
            container.put(item, item.getSequence(container.size()));
        }
        return this;
    }

    public ErrorCodeMapperBuilder customize(CustomizeFeedback... items) {
        for (Feedback item : items) {
            if (item.isCustom()) {
                Map<Feedback, Integer> config = customizeConfigs.get(item.getCustom());
                if (MapUtils.isEmpty(config)) {
                    config = new LinkedHashMap<>();
                }
                config.put(item, item.getSequence(config.size()));
                customizeConfigs.put(item.getCustom(), config);
            }
        }
        return this;
    }

    /**
     * 401	Unauthorized	请求要求用户的身份认证
     *
     * @param items 错误标识条目  {@link UnauthorizedFeedback}
     * @return {@link ErrorCodeMapperBuilder}
     */
    public ErrorCodeMapperBuilder unauthorized(UnauthorizedFeedback... items) {
        return create(this.unauthorizedConfigs, items);
    }

    /**
     * 403	Forbidden	服务器理解请求客户端的请求，但是拒绝执行此请求
     *
     * @param items 错误标识条目 {@link ForbiddenFeedback}
     * @return {@link ErrorCodeMapperBuilder}
     */
    public ErrorCodeMapperBuilder forbidden(ForbiddenFeedback... items) {
        return create(this.forbiddenConfigs, items);
    }

    /**
     * 404	Not Found	服务器无法根据客户端的请求找到资源（网页）。通过此代码，网站设计人员可设置"您所请求的资源无法找到"的个性页面
     *
     * @param items 错误标识条目 {@link NotFoundFeedback}
     * @return {@link NotFoundFeedback}
     */
    public ErrorCodeMapperBuilder notFound(NotFoundFeedback... items) {
        return create(this.notFoundConfigs, items);
    }

    /**
     * 405	Method Not Allowed	客户端请求中的方法被禁止
     *
     * @param items 错误标识条目 {@link MethodNotAllowedFeedback}
     * @return {@link ErrorCodeMapperBuilder}
     */
    public ErrorCodeMapperBuilder methodNotAllowed(MethodNotAllowedFeedback... items) {
        return create(this.methodNotAllowedConfigs, items);
    }

    /**
     * 406	Not Acceptable	服务器无法根据客户端请求的内容特性完成请求
     *
     * @param items 错误标识条目 {@link NotAcceptableFeedback}
     * @return {@link ErrorCodeMapperBuilder}
     */
    public ErrorCodeMapperBuilder notAcceptable(NotAcceptableFeedback... items) {
        return create(this.notAcceptableConfigs, items);
    }

    /**
     * 412	Precondition Failed	客户端请求信息的先决条件错误
     *
     * @param items 错误标识条目 {@link PreconditionFailedFeedback}
     * @return {@link ErrorCodeMapperBuilder}
     */
    public ErrorCodeMapperBuilder preconditionFailed(PreconditionFailedFeedback... items) {
        return create(this.preconditionFailedConfigs, items);
    }

    /**
     * 415	Unsupported Media Type	服务器无法处理请求附带的媒体格式
     *
     * @param items 错误标识条目 {@link UnsupportedMediaTypeFeedback}
     * @return {@link ErrorCodeMapperBuilder}
     */
    public ErrorCodeMapperBuilder unsupportedMediaType(UnsupportedMediaTypeFeedback... items) {
        return create(this.unsupportedMediaTypeConfigs, items);
    }

    /**
     * 500	Internal Server Error	服务器内部错误，无法完成请求
     *
     * @param items 错误标识条目 {@link InternalServerErrorFeedback}
     * @return {@link ErrorCodeMapperBuilder}
     */
    public ErrorCodeMapperBuilder internalServerError(InternalServerErrorFeedback... items) {
        return create(this.internalServerErrorConfigs, items);
    }

    /**
     * 501	Not Implemented	服务器不支持请求的功能，无法完成请求
     *
     * @param items 错误标识条目 {@link NotImplementedFeedback}
     * @return {@link ErrorCodeMapperBuilder}
     */
    public ErrorCodeMapperBuilder notImplemented(NotImplementedFeedback... items) {
        return create(this.notImplementedConfigs, items);
    }

    /**
     * 503	Service Unavailable	由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     *
     * @param items 错误标识条目 {@link ServiceUnavailableFeedback}
     * @return {@link ErrorCodeMapperBuilder}
     */
    public ErrorCodeMapperBuilder serviceUnavailable(ServiceUnavailableFeedback... items) {
        return create(this.serviceUnavailableConfigs, items);
    }

    public ErrorCodeMapper build() {
        ErrorCodeMapper errorCodeMapper = ErrorCodeMapper.getInstance();
        errorCodeMapper.append(unauthorizedConfigs);
        errorCodeMapper.append(forbiddenConfigs);
        errorCodeMapper.append(methodNotAllowedConfigs);
        errorCodeMapper.append(notAcceptableConfigs);
        errorCodeMapper.append(preconditionFailedConfigs);
        errorCodeMapper.append(unsupportedMediaTypeConfigs);
        errorCodeMapper.append(internalServerErrorConfigs);
        errorCodeMapper.append(notImplementedConfigs);
        errorCodeMapper.append(serviceUnavailableConfigs);
        errorCodeMapper.append(notFoundConfigs);

        customizeConfigs.forEach((key, feedbacks) -> errorCodeMapper.append(feedbacks));
        return errorCodeMapper;
    }
}
