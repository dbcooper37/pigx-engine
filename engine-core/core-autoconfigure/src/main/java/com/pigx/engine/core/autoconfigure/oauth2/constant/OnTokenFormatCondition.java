package com.pigx.engine.autoconfigure.oauth2.constant;

import com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition;
import java.lang.annotation.Annotation;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/oauth2/constant/OnTokenFormatCondition.class */
class OnTokenFormatCondition extends AbstractEnumSpringBootCondition<TokenFormat> {
    OnTokenFormatCondition() {
    }

    @Override // com.pigx.engine.core.foundation.condition.AbstractEnumSpringBootCondition
    protected Class<? extends Annotation> getAnnotationClass() {
        return ConditionalOnTokenFormat.class;
    }
}
