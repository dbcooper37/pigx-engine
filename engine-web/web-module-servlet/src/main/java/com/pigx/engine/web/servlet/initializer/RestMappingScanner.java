package com.pigx.engine.web.servlet.initializer;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.message.core.definition.strategy.RestMappingScanEventManager;
import com.pigx.engine.message.core.domain.RestMapping;
import com.pigx.engine.web.core.support.WebPropertyFinder;
import com.pigx.engine.web.service.initializer.AbstractRestMappingScanner;
import com.pigx.engine.web.service.properties.ServiceProperties;
import cn.hutool.v7.crypto.SecureUtil;
import io.swagger.v3.oas.annotations.Operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/* loaded from: web-module-servlet-3.5.7.0.jar:cn/herodotus/engine/web/servlet/initializer/RestMappingScanner.class */
public class RestMappingScanner extends AbstractRestMappingScanner {
    private static final Logger log = LoggerFactory.getLogger(RestMappingScanner.class);

    public RestMappingScanner(ServiceProperties.Scan scan, RestMappingScanEventManager restMappingScanEventManager) {
        super(scan, restMappingScanEventManager);
    }

    @Override // com.pigx.engine.web.service.initializer.AbstractRestMappingScanner
    public void onApplicationEvent(ApplicationContext applicationContext) {
        String contextPath = getContextPath(applicationContext);
        String serviceId = WebPropertyFinder.getApplicationName(applicationContext);
        if (notExecuteScanning()) {
            log.warn("[Herodotus] |- Can not found scan annotation in Service [{}], Skip!", serviceId);
            return;
        }
        Map<String, RequestMappingHandlerMapping> mappings = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);
        List<RestMapping> resources = new ArrayList<>();
        for (RequestMappingHandlerMapping mapping : mappings.values()) {
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
            if (MapUtils.isNotEmpty(handlerMethods)) {
                for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
                    RequestMappingInfo requestMappingInfo = entry.getKey();
                    HandlerMethod handlerMethod = entry.getValue();
                    if (!isExcludedRequestMapping(handlerMethod)) {
                        RestMapping restMapping = createRestMapping(serviceId, requestMappingInfo, handlerMethod, contextPath);
                        if (!ObjectUtils.isEmpty(restMapping)) {
                            resources.add(restMapping);
                        }
                    }
                }
            }
        }
        complete(serviceId, resources);
    }

    private RestMapping createRestMapping(String serviceId, RequestMappingInfo info, HandlerMethod method, String contextPath) {
        String className = method.getBeanType().getName();
        if (isLegalGroup(className)) {
            return null;
        }
        method.getBeanType().getSimpleName();
        String methodName = method.getMethod().getName();
        RequestMethodsRequestCondition requestMethodsRequestCondition = info.getMethodsCondition();
        String requestMethods = StringUtils.join(requestMethodsRequestCondition.getMethods(), SymbolConstants.COMMA);
        PathPatternsRequestCondition pathPatternsCondition = info.getPathPatternsCondition();
        Set<String> patternValues = pathPatternsCondition.getPatternValues();
        if (CollectionUtils.isEmpty(patternValues)) {
            return null;
        }
        String urls = (String) patternValues.stream().map(url -> {
            return toInterface(contextPath, url);
        }).collect(Collectors.joining(SymbolConstants.COMMA));
        String flag = serviceId + "-" + requestMethods + "-" + urls;
        String id = SecureUtil.md5(flag);
        RestMapping restMapping = new RestMapping();
        restMapping.setMappingId(id);
        restMapping.setMappingCode(createCode(urls, requestMethods));
        restMapping.setServiceId(serviceId);
        Operation apiOperation = method.getMethodAnnotation(Operation.class);
        if (ObjectUtils.isNotEmpty(apiOperation)) {
            restMapping.setDescription(apiOperation.summary());
        }
        restMapping.setRequestMethod(requestMethods);
        restMapping.setUrl(urls);
        restMapping.setClassName(className);
        restMapping.setMethodName(methodName);
        return restMapping;
    }
}
