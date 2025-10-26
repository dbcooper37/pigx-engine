package com.pigx.engine.message.core.definition.strategy;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import org.apache.commons.lang3.Strings;
import org.springframework.context.ApplicationEvent;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/definition/strategy/StrategyEventManager.class */
public interface StrategyEventManager<T> {
    void postLocalProcess(T data);

    void postRemoteProcess(String data, String originService, String destinationService);

    default boolean isLocal(String destinationService) {
        return !ServiceContextHolder.isDistributedArchitecture() || Strings.CS.equals(ServiceContextHolder.getApplicationName(), destinationService);
    }

    default void postProcess(String destinationService, T data) {
        postProcess(ServiceContextHolder.getOriginService(), destinationService, data);
    }

    default void postProcess(String originService, String destinationService, T data) {
        if (isLocal(destinationService)) {
            postLocalProcess(data);
        } else {
            postRemoteProcess(Jackson2Utils.toJson(data), originService, destinationService);
        }
    }

    default void publishEvent(ApplicationEvent event) {
        ServiceContextHolder.publishEvent(event);
    }
}
