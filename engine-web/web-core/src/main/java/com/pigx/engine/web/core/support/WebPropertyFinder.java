package com.pigx.engine.web.core.support;


import com.pigx.engine.core.definition.constant.BaseConstants;
import com.pigx.engine.core.foundation.context.PropertyResolver;
import com.pigx.engine.web.core.constant.WebConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ConditionContext;


public class WebPropertyFinder {

    public static String getApplicationName(ApplicationContext applicationContext) {
        return PropertyResolver.getProperty(applicationContext.getEnvironment(), BaseConstants.ITEM_SPRING_APPLICATION_NAME);
    }

    public static String getContextPath(ApplicationContext applicationContext) {
        return PropertyResolver.getProperty(applicationContext.getEnvironment(), BaseConstants.ITEM_SERVLET_CONTEXT_PATH);
    }

    public static String getCryptoStrategy(ConditionContext conditionContext, String defaultValue) {
        return PropertyResolver.getProperty(conditionContext, WebConstants.ITEM_PROTECT_CRYPTO_STRATEGY, defaultValue);
    }

    public static String getCryptoStrategy(ConditionContext conditionContext) {
        return PropertyResolver.getProperty(conditionContext, WebConstants.ITEM_PROTECT_CRYPTO_STRATEGY);
    }
}
