package com.pigx.engine.core.foundation.condition;

import java.lang.annotation.Annotation;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public abstract class AbstractEnumSpringBootCondition<T extends ConditionEnum> extends SpringBootCondition {
    protected abstract Class<? extends Annotation> getAnnotationClass();
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(this.getAnnotationClass().getName());
        T enums = (T)(attributes.get("value"));
        return this.getMatchOutcome(context.getEnvironment(), enums);
    }

    private ConditionOutcome getMatchOutcome(Environment environment, T enums) {
        String name = enums.getConstant();
        ConditionMessage.Builder message = ConditionMessage.forCondition(this.getAnnotationClass(), new Object[0]);
        return enums.isActive(environment) ? ConditionOutcome.match(message.foundExactly(name)) : ConditionOutcome.noMatch(message.didNotFind(name).atAll());
    }
}
