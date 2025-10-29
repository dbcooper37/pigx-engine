package com.pigx.engine.core.autoconfigure.error;

import com.pigx.engine.core.definition.builder.ErrorCodeMapperBuilder;
import com.pigx.engine.core.definition.domain.ErrorCodeMapper;
import com.pigx.engine.core.definition.function.ErrorCodeMapperBuilderCustomizer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;


@AutoConfiguration
public class ErrorCodeAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ErrorCodeAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[PIGXD] |- Auto [Error Code] Configure.");
    }

    @Bean
    public ErrorCodeMapperBuilderCustomizer standardErrorCodeMapperBuilderCustomizer() {
        StandardErrorCodeMapperBuilderCustomizer customizer = new StandardErrorCodeMapperBuilderCustomizer();
        log.debug("[PIGXD] |- Strategy [Standard ErrorCodeMapper Builder Customizer] Configure.");
        return customizer;
    }

    @Bean
    public ErrorCodeMapperBuilder errorCodeMapperBuilder(List<ErrorCodeMapperBuilderCustomizer> customizers) {
        ErrorCodeMapperBuilder builder = new ErrorCodeMapperBuilder();
        customize(builder, customizers);
        log.trace("[PIGXD] |- Bean [ErrorCodeMapper Builder] Configure.");
        return builder;
    }

    private void customize(ErrorCodeMapperBuilder builder, List<ErrorCodeMapperBuilderCustomizer> customizers) {
        for (ErrorCodeMapperBuilderCustomizer customizer : customizers) {
            customizer.customize(builder);
        }
    }

    @Bean
    public ErrorCodeMapper errorCodeMapper(ErrorCodeMapperBuilder builder) {
        ErrorCodeMapper mapper = builder.build();
        log.trace("[PIGXD] |- Bean [ErrorCodeMapper] Configure.");
        return mapper;
    }
}
