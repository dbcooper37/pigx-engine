package com.pigx.engine.web.service.initializer;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.definition.utils.WellFormedUtils;
import com.pigx.engine.message.core.definition.strategy.RestMappingScanEventManager;
import com.pigx.engine.message.core.domain.RestMapping;
import com.pigx.engine.web.core.support.WebPropertyFinder;
import com.pigx.engine.web.service.properties.ServiceProperties;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/initializer/AbstractRestMappingScanner.class */
public abstract class AbstractRestMappingScanner implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger log = LoggerFactory.getLogger(AbstractRestMappingScanner.class);
    private final ServiceProperties.Scan scan;
    private final RestMappingScanEventManager restMappingScanEventManager;

    protected abstract void onApplicationEvent(ApplicationContext applicationContext);

    protected AbstractRestMappingScanner(ServiceProperties.Scan scan, RestMappingScanEventManager restMappingScanEventManager) {
        this.scan = scan;
        this.restMappingScanEventManager = restMappingScanEventManager;
    }

    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        log.debug("[Herodotus] |- [R1] Application is READY, start to scan request mapping!");
        onApplicationEvent((ApplicationContext) applicationContext);
    }

    protected boolean isExcludedRequestMapping(HandlerMethod handlerMethod) {
        return (isSpringAnnotationMatched(handlerMethod) && isSwaggerAnnotationMatched(handlerMethod)) ? false : true;
    }

    protected boolean isSpringAnnotationMatched(HandlerMethod handlerMethod) {
        return (this.scan.getJustScanRestController().booleanValue() && handlerMethod.getMethod().getDeclaringClass().getAnnotation(RestController.class) == null) ? false : true;
    }

    protected boolean isSwaggerAnnotationMatched(HandlerMethod handlerMethod) {
        if (handlerMethod.getMethodAnnotation(Hidden.class) != null) {
            return false;
        }
        Operation operation = handlerMethod.getMethodAnnotation(Operation.class);
        return ObjectUtils.isNotEmpty(operation) && !operation.hidden();
    }

    protected boolean isLegalGroup(String className) {
        if (StringUtils.isNotEmpty(className)) {
            List<String> groupIds = this.scan.getScanGroupIds();
            List<String> result = (List) groupIds.stream().filter(groupId -> {
                return Strings.CS.contains(className, groupId);
            }).collect(Collectors.toList());
            return CollectionUtils.sizeIsEmpty(result);
        }
        return true;
    }

    protected String createCode(String url, String requestMethods) {
        String[] search = {SymbolConstants.OPEN_CURLY_BRACE, "}", "/"};
        String[] replacement = {SymbolConstants.BLANK, SymbolConstants.BLANK, SymbolConstants.COLON};
        String code = StringUtils.replaceEach(url, search, replacement);
        String result = StringUtils.isNotBlank(requestMethods) ? StringUtils.lowerCase(requestMethods) + code : Strings.CS.removeStart(code, SymbolConstants.COLON);
        log.trace("[Herodotus] |- Create code [{}] for Request [{}] : [{}]", new Object[]{result, requestMethods, url});
        return result;
    }

    protected boolean notExecuteScanning() {
        return !this.restMappingScanEventManager.isPerformScan();
    }

    protected void complete(String serviceId, List<RestMapping> resources) {
        if (CollectionUtils.isNotEmpty(resources)) {
            log.debug("[Herodotus] |- [R2] Request mapping scan found [{}] resources in service [{}], go to next stage!", serviceId, Integer.valueOf(resources.size()));
            this.restMappingScanEventManager.postProcess(resources);
        } else {
            log.debug("[Herodotus] |- [R2] Request mapping scan can not find any resources in service [{}]!", serviceId);
        }
        log.info("[Herodotus] |- Request Mapping Scan for Service: [{}] FINISHED!", serviceId);
    }

    protected String getContextPath(ApplicationContext applicationContext) {
        String contextPath = WebPropertyFinder.getContextPath(applicationContext);
        if (StringUtils.isNotBlank(contextPath) && !Strings.CS.equals(contextPath, "/")) {
            return WellFormedUtils.robustness(contextPath, "/", false, false);
        }
        return SymbolConstants.BLANK;
    }

    protected String toInterface(String contextPath, String url) {
        if (StringUtils.isBlank(contextPath)) {
            return url;
        }
        return contextPath + url;
    }
}
