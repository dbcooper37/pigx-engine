package com.pigx.engine.servlet.message.autoconfigure.customizer;

import com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder;
import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.message.core.constants.MessageErrorCodes;
import org.springframework.core.Ordered;


public class MessageErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override
    public void customize(ErrorCodeMapperBuilder builder) {
        builder.notAcceptable(MessageErrorCodes.ILLEGAL_CHANNEL, MessageErrorCodes.PRINCIPAL_NOT_FOUND);
    }

    @Override
    public int getOrder() {
        return ErrorCodeMapperBuilderOrdered.MESSAGE;
    }
}
