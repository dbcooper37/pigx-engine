package com.pigx.engine.core.autoconfigure.client.servlet.feign;

import com.fasterxml.jackson.databind.JavaType;
import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.core.foundation.exception.feigin.FeignDecodeIOException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class FeignErrorDecoder implements ErrorDecoder {

    private static final Logger log = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            String content = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            Result<String> result = Result.failure("Feign 远程调用" + methodKey + " 出错");
            JavaType javaType = Jackson2Utils.getTypeFactory().constructParametricType(Result.class, String.class);
            Result<String> object = Jackson2Utils.toObject(content, javaType);
            if (ObjectUtils.isEmpty(object)) {
                result = object;
            }
            return new FeignRemoteCallExceptionWrapper(result);
        } catch (IOException e) {
            log.error("[PIGXD] |- Feign invoke [{}] error decoder convert result catch io exception.", methodKey, e);
            return new FeignDecodeIOException();
        }
    }
}
