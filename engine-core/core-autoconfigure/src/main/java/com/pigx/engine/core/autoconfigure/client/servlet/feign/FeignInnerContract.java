package com.pigx.engine.autoconfigure.client.servlet.feign;

import com.pigx.engine.core.definition.annotation.Inner;
import com.pigx.engine.core.definition.constant.HerodotusHeaders;
import feign.MethodMetadata;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;

/* loaded from: core-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/core/autoconfigure/client/servlet/feign/FeignInnerContract.class */
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

    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
        if (methodAnnotation instanceof Inner) {
            Inner inner = (Inner) AnnotatedElementUtils.findMergedAnnotation(method, Inner.class);
            if (ObjectUtils.isNotEmpty(inner)) {
                log.debug("[Herodotus] |- Found inner annotation on Feign interface, add header!");
                data.template().header(HerodotusHeaders.X_HERODOTUS_FROM_IN, new String[]{"true"});
            }
        }
        super.processAnnotationOnMethod(data, methodAnnotation, method);
    }
}
