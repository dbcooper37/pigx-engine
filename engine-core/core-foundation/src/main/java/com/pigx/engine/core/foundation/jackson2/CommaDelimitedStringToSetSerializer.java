package com.pigx.engine.core.foundation.jackson2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pigx.engine.core.definition.constant.SymbolConstants;
import org.apache.commons.lang3.Strings;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class CommaDelimitedStringToSetSerializer extends StdSerializer<String> {
    public CommaDelimitedStringToSetSerializer() {
        super(String.class);
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Set<String> collection = new HashSet<>();
        if (StringUtils.hasText(value)) {
            if (Strings.CS.contains(value, SymbolConstants.COMMA)) {
                collection = StringUtils.commaDelimitedListToSet(value);
            } else {
                collection.add(value);
            }
        }

        int len = collection.size();

        gen.writeStartArray(collection, len);
        serializeContents(collection, gen, provider);
        gen.writeEndArray();
    }

    private void serializeContents(Set<String> value, JsonGenerator g, SerializerProvider provider) throws IOException {
        int i = 0;

        try {
            for (String str : value) {
                if (str == null) {
                    provider.defaultSerializeNull(g);
                } else {
                    g.writeString(str);
                }
                ++i;
            }
        } catch (Exception e) {
            wrapAndThrow(provider, e, value, i);
        }
    }
}
