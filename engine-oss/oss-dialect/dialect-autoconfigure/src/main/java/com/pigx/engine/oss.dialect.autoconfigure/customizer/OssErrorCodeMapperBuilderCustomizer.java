package com.pigx.engine.oss.dialect.autoconfigure.customizer;

import com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder;
import com.pigx.engine.core.definition.constant.ErrorCodeMapperBuilderOrdered;
import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import com.pigx.engine.oss.dialect.core.constants.OssErrorCodes;
import org.springframework.core.Ordered;


public class OssErrorCodeMapperBuilderCustomizer implements ErrorCodeMapperBuilderCustomizer, Ordered {
    @Override
    public void customize(ErrorCodeMapperBuilder builder) {
        builder
                .internalServerError(
                        OssErrorCodes.OSS_CLIENT_POOL_ERROR,
                        OssErrorCodes.OSS_ERROR_RESPONSE,
                        OssErrorCodes.OSS_INSUFFICIENT_DATA,
                        OssErrorCodes.OSS_INTERNAL,
                        OssErrorCodes.OSS_INVALID_KEY,
                        OssErrorCodes.OSS_INVALID_RESPONSE,
                        OssErrorCodes.OSS_IO,
                        OssErrorCodes.OSS_NO_SUCH_ALGORITHM,
                        OssErrorCodes.OSS_SERVER,
                        OssErrorCodes.OSS_XML_PARSER,
                        OssErrorCodes.OSS_EXECUTION,
                        OssErrorCodes.OSS_INTERRUPTED,
                        OssErrorCodes.OSS_BUCKET_POLICY_TOO_LARGE,
                        OssErrorCodes.OSS_INVALID_CIPHER_TEXT)
                .serviceUnavailable(OssErrorCodes.OSS_CONNECTION);
    }

    @Override
    public int getOrder() {
        return ErrorCodeMapperBuilderOrdered.OSS;
    }
}
