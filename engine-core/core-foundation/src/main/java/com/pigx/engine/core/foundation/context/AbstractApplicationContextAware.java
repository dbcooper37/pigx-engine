package com.pigx.engine.core.foundation.context;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/context/AbstractApplicationContextAware.class */
public abstract class AbstractApplicationContextAware implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    protected ApplicationContext getApplicationContext() {
        if (ObjectUtils.isEmpty(this.applicationContext)) {
            return ServiceContextHolder.getApplicationContext();
        }
        return this.applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected void publishEvent(ApplicationEvent event) {
        getApplicationContext().publishEvent(event);
    }
}
