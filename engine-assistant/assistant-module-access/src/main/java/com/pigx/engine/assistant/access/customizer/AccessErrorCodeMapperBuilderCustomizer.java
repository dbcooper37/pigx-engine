package com.pigx.engine.assistant.access.customizer;

import com.pigx.engine.assistant.access.constant.AccessErrorCodes;
import com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder;
import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import org.springframework.core.Ordered;

/* loaded from: assistant-module-access-3.5.7.0.jar:cn/herodotus/engine/assistant/access/customizer/AccessErrorCodeMapperBuilderCustomizer.class */
public class AccessErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override // com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer
    public void customize(ErrorCodeMapperBuilder builder) {
        builder.preconditionFailed(AccessErrorCodes.ACCESS_CONFIG_ERROR, AccessErrorCodes.ACCESS_HANDLER_NOT_FOUND, AccessErrorCodes.ACCESS_IDENTITY_VERIFICATION_FAILED, AccessErrorCodes.ACCESS_PRE_PROCESS_FAILED_EXCEPTION, AccessErrorCodes.ILLEGAL_ACCESS_ARGUMENT, AccessErrorCodes.ILLEGAL_ACCESS_SOURCE);
    }

    public int getOrder() {
        return 60;
    }
}
