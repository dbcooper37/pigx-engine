package com.pigx.engine.oauth2.authorization.autoconfigure.listener;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.message.core.domain.RestMapping;
import com.pigx.engine.oauth2.authorization.autoconfigure.bus.RemoteRestMappingGatherEvent;
import com.pigx.engine.oauth2.authorization.autoconfigure.processor.AttributeTransmitterDistributeProcessor;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/* JADX WARN: Classes with same name are omitted:
  
 */
@Component
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/listener/RemoteRestMappingGatherListener.class */
public class RemoteRestMappingGatherListener implements ApplicationListener<RemoteRestMappingGatherEvent> {
    private static final Logger log = LoggerFactory.getLogger(RemoteRestMappingGatherListener.class);
    private final AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor;

    public RemoteRestMappingGatherListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
        this.attributeTransmitterDistributeProcessor = attributeTransmitterDistributeProcessor;
    }

    public void onApplicationEvent(RemoteRestMappingGatherEvent event) {
        log.info("[Herodotus] |- Request mapping gather REMOTE listener, response service [{}] event!", event.getOriginService());
        String requestMapping = event.getData();
        log.debug("[Herodotus] |- [R4] Request mapping process BEGIN!");
        Optional optionalFlatMap = Optional.ofNullable(requestMapping).flatMap(value -> {
            return Optional.ofNullable(Jackson2Utils.toList(value, RestMapping.class));
        });
        AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor = this.attributeTransmitterDistributeProcessor;
        Objects.requireNonNull(attributeTransmitterDistributeProcessor);
        optionalFlatMap.ifPresent(attributeTransmitterDistributeProcessor::postRestMappings);
    }
}
