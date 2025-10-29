package com.pigx.engine.facility.alibaba.autoconfigure.sentinel.enhance;

import feign.Target;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cloud.openfeign.FallbackFactory;


public class HerodotusFallbackFactory<T> implements FallbackFactory<T> {

    private final Target<T> target;

    public HerodotusFallbackFactory(Target<T> target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T create(Throwable cause) {
        final Class<T> targetType = target.type();
        final String targetName = target.name();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetType);
        enhancer.setUseCache(true);
        enhancer.setCallback(new HerodotusFeignFallback<>(targetType, targetName, cause));
        return (T) enhancer.create();
    }

}
