package com.pigx.engine.autoconfigure.client.servlet.feign;

import com.pigx.engine.core.definition.domain.Result;
import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.core.foundation.exception.feigin.FeignDecodeIOException;
import com.fasterxml.jackson.databind.JavaType;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/servlet/feign/FeignErrorDecoder.class */
public class FeignErrorDecoder implements ErrorDecoder {
    private static final Logger log = LoggerFactory.getLogger(FeignErrorDecoder.class);

    public Exception decode(String methodKey, Response response) {
        try {
            String content = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
            Result<String> result = Result.failure("Feign 远程调用" + methodKey + " 出错");
            JavaType javaType = Jackson2Utils.getTypeFactory().constructParametricType(Result.class, new Class[]{String.class});
            Result<String> object = (Result) Jackson2Utils.toObject(content, javaType);
            if (ObjectUtils.isEmpty(object)) {
                result = object;
            }
            return new FeignRemoteCallExceptionWrapper(result);
        } catch (IOException e) {
            log.error("[Herodotus] |- Feign invoke [{}] error decoder convert result catch io exception.", methodKey, e);
            return new FeignDecodeIOException();
        }
    }
}
