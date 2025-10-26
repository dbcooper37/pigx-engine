package com.pigx.engine.logic.upms.domain.deserializer;

import com.pigx.engine.logic.upms.entity.hr.SysEmployee;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/domain/deserializer/SysEmployeeEmptyToNull.class */
public class SysEmployeeEmptyToNull extends JsonDeserializer<SysEmployee> {
    /* renamed from: deserialize, reason: merged with bridge method [inline-methods] */
    public SysEmployee m138deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode jsonNode = jsonParser.readValueAsTree();
        if (jsonNode.isEmpty() || jsonNode.isNull()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return (SysEmployee) objectMapper.treeToValue(jsonNode, SysEmployee.class);
    }
}
