package com.pigx.engine.message.core.definition.strategy;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/definition/strategy/ApplicationStrategyEventManager.class */
public interface ApplicationStrategyEventManager<T> extends StrategyEventManager<T> {
    String getDestinationServiceName();

    default void postProcess(T data) {
        postProcess(getDestinationServiceName(), data);
    }
}
