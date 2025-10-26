package com.pigx.engine.core.foundation.jackson2;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/* loaded from: core-foundation-3.5.7.0.jar:cn/herodotus/engine/core/foundation/jackson2/EncapsulationClassJackson2Module.class */
public class EncapsulationClassJackson2Module extends SimpleModule {
    public EncapsulationClassJackson2Module() {
        addSerializer(Long.class, ToStringSerializer.instance);
        addSerializer(Long.TYPE, ToStringSerializer.instance);
    }
}
