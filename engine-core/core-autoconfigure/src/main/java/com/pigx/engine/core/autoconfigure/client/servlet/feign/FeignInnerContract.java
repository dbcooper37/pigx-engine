package com.pigx.engine.core.autoconfigure.client.servlet.feign;

import com.pigx.engine.core.definition.annotation.Inner;
import com.pigx.engine.core.definition.constant.HerodotusHeaders;
import feign.MethodMetadata;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.convert.ConversionService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;


public class FeignInnerContract extends SpringMvcContract {

    private static final Logger log = LoggerFactory.getLogger(FeignInnerContract.class);

    public FeignInnerContract() {
    }

    public FeignInnerContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors) {
        super(annotatedParameterProcessors);
    }

    public FeignInnerContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors, ConversionService conversionService) {
        super(annotatedParameterProcessors, conversionService);
    }

    public FeignInnerContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors, ConversionService conversionService, FeignClientProperties feignClientProperties) {
        super(annotatedParameterProcessors, conversionService, feignClientProperties);
    }

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {

        if (methodAnnotation instanceof Inner) {
            Inner inner = findMergedAnnotation(method, Inner.class);
            if (ObjectUtils.isNotEmpty(inner)) {
                log.debug("[PIGXD] |- Found inner annotation on Feign interface, add header!");
                data.template().header(HerodotusHeaders.X_HERODOTUS_FROM_IN, "true");
            }
        }

        super.processAnnotationOnMethod(data, methodAnnotation, method);
    }
}
