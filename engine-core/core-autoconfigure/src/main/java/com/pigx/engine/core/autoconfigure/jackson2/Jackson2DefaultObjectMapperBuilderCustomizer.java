package com.pigx.engine.core.autoconfigure.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pigx.engine.core.definition.constant.Jackson2CustomizerOrder;
import com.pigx.engine.core.foundation.support.BaseObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class Jackson2DefaultObjectMapperBuilderCustomizer implements BaseObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {

        builder.timeZone(TimeZone.getDefault());

        builder.featuresToEnable(
                SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
                JsonParser.Feature.ALLOW_SINGLE_QUOTES,
                JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature()
        );

        builder.featuresToDisable(
                SerializationFeature.FAIL_ON_EMPTY_BEANS,
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        );

        builder.modulesToInstall(
                modules -> {
                    List<Module> install = new ArrayList<>(modules);
                    install.add(new Jdk8Module());
                    install.add(new JavaTimeModule());
                    builder.modulesToInstall(toArray(install));
                }
        );

        builder.findModulesViaServiceLoader(true);
    }

    @Override
    public int getOrder() {
        return Jackson2CustomizerOrder.CUSTOMIZER_DEFAULT;
    }
}
