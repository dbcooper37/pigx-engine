package com.pigx.engine.core.foundation.support;

import com.fasterxml.jackson.databind.Module;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.core.Ordered;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/support/BaseObjectMapperBuilderCustomizer.class */
public interface BaseObjectMapperBuilderCustomizer extends Jackson2ObjectMapperBuilderCustomizer, Ordered {
    default Module[] toArray(List<Module> modules) {
        if (CollectionUtils.isNotEmpty(modules)) {
            Module[] temps = new Module[modules.size()];
            return (Module[]) modules.toArray(temps);
        }
        return new Module[0];
    }
}
