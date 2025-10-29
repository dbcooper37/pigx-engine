package com.pigx.engine.message.core.definition.strategy;


public interface ApplicationStrategyEventManager<T> extends StrategyEventManager<T> {

    /**
     * 目的服务名称
     *
     * @return 服务名称
     */
    String getDestinationServiceName();

    /**
     * 发送事件
     *
     * @param data 事件携带数据
     */
    default void postProcess(T data) {
        postProcess(getDestinationServiceName(), data);
    }
}
