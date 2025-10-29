package com.pigx.engine.core.foundation.context;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;


public abstract class AbstractApplicationContextAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    protected ApplicationContext getApplicationContext() {
        if (ObjectUtils.isEmpty(applicationContext)) {
            return ServiceContextHolder.getApplicationContext();
        }
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected void publishEvent(ApplicationEvent event) {
        this.getApplicationContext().publishEvent(event);
    }
}
