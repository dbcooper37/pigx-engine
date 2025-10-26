package com.pigx.engine.web.service.customizer;

import com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder;
import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.web.core.constant.WebErrorCodes;
import org.springframework.core.Ordered;

/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/customizer/WebErrorCodeMapperBuilderCustomizer.class */
public class WebErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override // com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer
    public void customize(ErrorCodeMapperBuilder builder) {
        builder.notAcceptable(WebErrorCodes.SESSION_INVALID, WebErrorCodes.REPEAT_SUBMISSION, WebErrorCodes.FREQUENT_REQUESTS, WebErrorCodes.FEIGN_DECODER_IO_EXCEPTION);
    }

    public int getOrder() {
        return 40;
    }
}
