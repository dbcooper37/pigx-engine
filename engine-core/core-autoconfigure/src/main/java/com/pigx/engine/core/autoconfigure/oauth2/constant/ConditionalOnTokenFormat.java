package com.pigx.engine.core.autoconfigure.oauth2.constant;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnTokenFormatCondition.class)
public @interface ConditionalOnTokenFormat {

    /**
     * {@link TokenFormat accessTokenFormat} 属性必须配置.
     *
     * @return 预期的 AccessToken 格式
     */
    TokenFormat value();
}
