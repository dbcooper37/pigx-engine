package com.pigx.engine.oauth2.authorization.autoconfigure.strategy;

import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.message.core.definition.strategy.RestMappingScanEventManager;
import com.pigx.engine.message.core.domain.RestMapping;
import com.pigx.engine.message.core.event.RestMappingGatherEvent;
import com.pigx.engine.oauth2.authorization.autoconfigure.bus.RemoteRestMappingGatherEvent;
import com.pigx.engine.oauth2.authorization.processor.SecurityAttributeAnalyzer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;


@Component
public class DefaultRestMappingScanEventManager implements RestMappingScanEventManager {

    private final SecurityAttributeAnalyzer securityAttributeAnalyzer;

    public DefaultRestMappingScanEventManager(SecurityAttributeAnalyzer securityAttributeAnalyzer) {
        this.securityAttributeAnalyzer = securityAttributeAnalyzer;
    }

    @Override
    public Class<? extends Annotation> getScanAnnotationClass() {
        return EnableWebSecurity.class;
    }

    @Override
    public String getDestinationServiceName() {
        return ServiceContextHolder.getUpmsServiceName();
    }

    @Override
    public void postLocalStorage(List<RestMapping> restMappings) {
        securityAttributeAnalyzer.processRequestMatchers();
    }

    @Override
    public void postLocalProcess(List<RestMapping> data) {
        publishEvent(new RestMappingGatherEvent(data));
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteRestMappingGatherEvent(data, originService, destinationService));
    }
}
