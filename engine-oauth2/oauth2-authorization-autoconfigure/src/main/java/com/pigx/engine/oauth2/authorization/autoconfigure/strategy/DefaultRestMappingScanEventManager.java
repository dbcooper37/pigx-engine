package com.pigx.engine.oauth2.authorization.autoconfigure.strategy;

import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.message.core.definition.strategy.RestMappingScanEventManager;
import com.pigx.engine.message.core.domain.RestMapping;
import com.pigx.engine.message.core.event.RestMappingGatherEvent;
import com.pigx.engine.oauth2.authorization.autoconfigure.bus.RemoteRestMappingGatherEvent;
import com.pigx.engine.oauth2.authorization.processor.SecurityAttributeAnalyzer;
import java.lang.annotation.Annotation;
import java.util.List;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;

/* JADX WARN: Classes with same name are omitted:
  
 */
@Component
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/strategy/DefaultRestMappingScanEventManager.class */
public class DefaultRestMappingScanEventManager implements RestMappingScanEventManager {
    private final SecurityAttributeAnalyzer securityAttributeAnalyzer;

    public DefaultRestMappingScanEventManager(SecurityAttributeAnalyzer securityAttributeAnalyzer) {
        this.securityAttributeAnalyzer = securityAttributeAnalyzer;
    }

    @Override // com.pigx.engine.message.core.definition.strategy.RestMappingScanEventManager
    public Class<? extends Annotation> getScanAnnotationClass() {
        return EnableWebSecurity.class;
    }

    @Override // com.pigx.engine.message.core.definition.strategy.ApplicationStrategyEventManager
    public String getDestinationServiceName() {
        return ServiceContextHolder.getUpmsServiceName();
    }

    @Override // com.pigx.engine.message.core.definition.strategy.RestMappingScanEventManager
    public void postLocalStorage(List<RestMapping> restMappings) {
        this.securityAttributeAnalyzer.processRequestMatchers();
    }

    @Override // com.pigx.engine.message.core.definition.strategy.StrategyEventManager
    public void postLocalProcess(List<RestMapping> data) {
        publishEvent(new RestMappingGatherEvent(data));
    }

    @Override // com.pigx.engine.message.core.definition.strategy.StrategyEventManager
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteRestMappingGatherEvent(data, originService, destinationService));
    }
}
