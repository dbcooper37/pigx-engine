package com.pigx.engine.message.autoconfigure.message;

import com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder;
import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.message.core.constants.MessageErrorCodes;
import org.springframework.core.Ordered;

public class MessageErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override // com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer
    public void customize(ErrorCodeMapperBuilder builder) {
        builder.notAcceptable(MessageErrorCodes.ILLEGAL_CHANNEL, MessageErrorCodes.PRINCIPAL_NOT_FOUND).internalServerError(MessageErrorCodes.INTEGRATION_MESSAGE_EXCEPTION);
    }

    public int getOrder() {
        return 50;
    }
}
