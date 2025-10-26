package com.pigx.engine.logic.identity.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hibernate.annotations.IdGeneratorType;

@Target({ElementType.FIELD, ElementType.METHOD})
@IdGeneratorType(OAuth2PermissionIdGeneratorType.class)
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/generator/OAuth2PermissionIdGenerator.class */
public @interface OAuth2PermissionIdGenerator {
}
