package com.pigx.engine.web.service.jackson2;

import com.pigx.engine.core.foundation.utils.XssUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/jackson2/XssStringJsonDeserializer.class */
public class XssStringJsonDeserializer extends JsonDeserializer<String> {
    public Class<String> handledType() {
        return String.class;
    }

    /* renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public String m249deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getValueAsString();
        if (StringUtils.isNotBlank(value)) {
            return XssUtils.process(value);
        }
        return value;
    }
}
