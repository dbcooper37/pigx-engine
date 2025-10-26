package com.pigx.engine.logic.upms.domain.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.IdGeneratorType;

@Target({ElementType.FIELD, ElementType.METHOD})
@IdGeneratorType(SysInterfaceIdGeneratorTypeType.class)
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/domain/generator/SysInterfaceIdGenerator.class */
public @interface SysInterfaceIdGenerator {
}
