package com.pigx.engine.core.foundation.condition;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.Annotation;
import java.util.Map;


public abstract class AbstractEnumSpringBootCondition<T extends ConditionEnum> extends SpringBootCondition {

    protected abstract Class<? extends Annotation> getAnnotationClass();

    @SuppressWarnings("unchecked")
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(getAnnotationClass().getName());
        T enums = (T) attributes.get("value");
        return getMatchOutcome(context.getEnvironment(), enums);
    }

    private ConditionOutcome getMatchOutcome(Environment environment, T enums) {
        String name = enums.getConstant();
        ConditionMessage.Builder message = ConditionMessage.forCondition(getAnnotationClass());
        if (enums.isActive(environment)) {
            return ConditionOutcome.match(message.foundExactly(name));
        } else {
            return ConditionOutcome.noMatch(message.didNotFind(name).atAll());
        }
    }
}
