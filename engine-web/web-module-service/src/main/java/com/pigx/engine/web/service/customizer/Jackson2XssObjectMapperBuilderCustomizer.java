package com.pigx.engine.web.service.customizer;

import com.pigx.engine.core.foundation.support.BaseObjectMapperBuilderCustomizer;
import com.pigx.engine.web.service.jackson2.XssStringJsonDeserializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/* loaded from: web-module-service-3.5.7.0.jar:cn/herodotus/engine/web/service/customizer/Jackson2XssObjectMapperBuilderCustomizer.class */
public class Jackson2XssObjectMapperBuilderCustomizer implements BaseObjectMapperBuilderCustomizer {
    public void customize(Jackson2ObjectMapperBuilder builder) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new XssStringJsonDeserializer());
        builder.modulesToInstall(modules -> {
            List<Module> install = new ArrayList<>(modules);
            install.add(simpleModule);
            builder.modulesToInstall(toArray(install));
        });
    }

    public int getOrder() {
        return 2;
    }
}
