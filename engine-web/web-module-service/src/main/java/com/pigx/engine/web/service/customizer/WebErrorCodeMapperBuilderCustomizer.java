package com.pigx.engine.web.service.customizer;

import com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder;
import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.web.core.constant.WebErrorCodes;
import org.springframework.core.Ordered;


public class WebErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override
    public void customize(ErrorCodeMapperBuilder builder) {
        builder.notAcceptable(
                WebErrorCodes.SESSION_INVALID,
                WebErrorCodes.REPEAT_SUBMISSION,
                WebErrorCodes.FREQUENT_REQUESTS,
                WebErrorCodes.FEIGN_DECODER_IO_EXCEPTION
        );
    }

    @Override
    public int getOrder() {
        return ErrorCodeMapperBuilderOrdered.REST;
    }
}
