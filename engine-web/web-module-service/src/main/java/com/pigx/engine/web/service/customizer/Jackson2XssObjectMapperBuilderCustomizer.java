package com.pigx.engine.web.service.customizer;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pigx.engine.core.definition.constant.Jackson2CustomizerOrder;
import com.pigx.engine.core.foundation.support.BaseObjectMapperBuilderCustomizer;
import com.pigx.engine.web.service.jackson2.XssStringJsonDeserializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.ArrayList;
import java.util.List;


public class Jackson2XssObjectMapperBuilderCustomizer implements BaseObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new XssStringJsonDeserializer());

        builder.modulesToInstall(modules -> {
            List<Module> install = new ArrayList<>(modules);
            install.add(simpleModule);
            builder.modulesToInstall(toArray(install));
        });
    }

    @Override
    public int getOrder() {
        return Jackson2CustomizerOrder.CUSTOMIZER_XSS;
    }
}
