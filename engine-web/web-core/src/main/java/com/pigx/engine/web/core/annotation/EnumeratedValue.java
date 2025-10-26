package com.pigx.engine.web.core.annotation;

import com.pigx.engine.web.core.validation.EnumeratedValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Constraint(validatedBy = {EnumeratedValueValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(List.class)
@Documented
/* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/annotation/EnumeratedValue.class */
public @interface EnumeratedValue {

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    /* loaded from: web-core-3.5.7.0.jar:cn/herodotus/engine/web/core/annotation/EnumeratedValue$List.class */
    public @interface List {
        EnumeratedValue[] value();
    }

    String message() default "必须为指定值";

    String[] names() default {};

    int[] ordinals() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
