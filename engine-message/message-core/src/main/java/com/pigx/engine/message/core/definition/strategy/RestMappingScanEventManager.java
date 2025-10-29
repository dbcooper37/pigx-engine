package com.pigx.engine.message.core.definition.strategy;

import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.message.core.domain.RestMapping;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;


public interface RestMappingScanEventManager extends ApplicationStrategyEventManager<List<RestMapping>> {

    /**
     * 获取是否执行扫描的标记注解。
     *
     * @return 标记注解
     */
    Class<? extends Annotation> getScanAnnotationClass();

    /**
     * 执行本地数据存储
     *
     * @param restMappings 扫描到的RequestMapping
     */
    void postLocalStorage(List<RestMapping> restMappings);

    /**
     * 发布远程事件，传送RequestMapping
     *
     * @param restMappings 扫描到的RequestMapping
     */
    @Override
    default void postProcess(List<RestMapping> restMappings) {
        postLocalStorage(restMappings);
        ApplicationStrategyEventManager.super.postProcess(restMappings);
    }

    /**
     * 是否满足执行扫描的条件。
     * 根据扫描标记注解 {@link #getScanAnnotationClass()} 以及 是否是分布式架构 决定是否执行接口的扫描。
     * <p>
     * 分布式架构根据注解判断是否扫描，单体架构直接扫描即可无须判断
     *
     * @return true 执行， false 不执行
     */
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
