package com.pigx.engine.message.core.definition.strategy;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import org.apache.commons.lang3.Strings;
import org.springframework.context.ApplicationEvent;


public interface StrategyEventManager<T> {

    /**
     * 创建本地事件
     *
     * @param data 事件携带数据
     */
    void postLocalProcess(T data);

    /**
     * 创建远程事件
     *
     * @param data               事件携带数据。JSON 格式的数据。
     * @param originService      发送远程事件原始服务
     * @param destinationService 接收远程事件目的地
     */
    void postRemoteProcess(String data, String originService, String destinationService);

    /**
     * 是否是本地处理事件。
     *
     * @param destinationService 接收远程事件目的地
     * @return false 远程事件，local 本地事件
     */
    default boolean isLocal(String destinationService) {
        return !ServiceContextHolder.isDistributedArchitecture() || Strings.CS.equals(ServiceContextHolder.getApplicationName(), destinationService);
    }

    /**
     * 发送事件
     *
     * @param data               事件携带数据
     * @param destinationService 接收远程事件目的地
     */
    default void postProcess(String destinationService, T data) {
        postProcess(ServiceContextHolder.getOriginService(), destinationService, data);
    }

    /**
     * 发送事件
     *
     * @param data               事件携带数据
     * @param originService      发送远程事件原始服务
     * @param destinationService 接收远程事件目的地
     */
    default void postProcess(String originService, String destinationService, T data) {
        if (isLocal(destinationService)) {
            postLocalProcess(data);
        } else {
            postRemoteProcess(Jackson2Utils.toJson(data), originService, destinationService);
        }
    }

    /**
     * 发送 Spring Event
     *
     * @param event 自定义 Spring Event
     */
    default void publishEvent(ApplicationEvent event) {
        ServiceContextHolder.publishEvent(event);
    }
}
