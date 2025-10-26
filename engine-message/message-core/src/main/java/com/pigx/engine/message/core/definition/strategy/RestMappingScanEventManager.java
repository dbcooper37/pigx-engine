package com.pigx.engine.message.core.definition.strategy;

import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.message.core.domain.RestMapping;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/definition/strategy/RestMappingScanEventManager.class */
public interface RestMappingScanEventManager extends ApplicationStrategyEventManager<List<RestMapping>> {
    Class<? extends Annotation> getScanAnnotationClass();

    void postLocalStorage(List<RestMapping> restMappings);

    @Override // com.pigx.engine.message.core.definition.strategy.ApplicationStrategyEventManager
    default void postProcess(List<RestMapping> restMappings) {
        postLocalStorage(restMappings);
        super.postProcess((RestMappingScanEventManager) restMappings);
    }

    default boolean isPerformScan() {
        if (ServiceContextHolder.isDistributedArchitecture()) {
            if (ObjectUtils.isEmpty(getScanAnnotationClass())) {
                return false;
            }
            Map<String, Object> content = ServiceContextHolder.getApplicationContext().getBeansWithAnnotation(getScanAnnotationClass());
            return !MapUtils.isEmpty(content);
        }
        return true;
    }
}
