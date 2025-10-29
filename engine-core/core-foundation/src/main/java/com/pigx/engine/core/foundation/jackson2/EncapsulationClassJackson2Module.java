package com.pigx.engine.core.foundation.jackson2;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


public class EncapsulationClassJackson2Module extends SimpleModule {

    public EncapsulationClassJackson2Module() {
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
    }
}
