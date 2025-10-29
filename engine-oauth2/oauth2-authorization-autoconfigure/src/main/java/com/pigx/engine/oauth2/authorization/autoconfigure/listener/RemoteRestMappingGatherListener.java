package com.pigx.engine.oauth2.authorization.autoconfigure.listener;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.message.core.domain.RestMapping;
import com.pigx.engine.oauth2.authorization.autoconfigure.bus.RemoteRestMappingGatherEvent;
import com.pigx.engine.oauth2.authorization.autoconfigure.processor.AttributeTransmitterDistributeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class RemoteRestMappingGatherListener implements ApplicationListener<RemoteRestMappingGatherEvent> {

    private static final Logger log = LoggerFactory.getLogger(RemoteRestMappingGatherListener.class);

    private final AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor;

    public RemoteRestMappingGatherListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
        this.attributeTransmitterDistributeProcessor = attributeTransmitterDistributeProcessor;
    }

    @Override
    public void onApplicationEvent(RemoteRestMappingGatherEvent event) {

        log.info("[PIGXD] |- Request mapping gather REMOTE listener, response service [{}] event!", event.getOriginService());

        String requestMapping = event.getData();

        log.debug("[PIGXD] |- [R4] Request mapping process BEGIN!");

        Optional.ofNullable(requestMapping)
                .flatMap(value -> Optional.ofNullable(Jackson2Utils.toList(value, RestMapping.class)))
                .ifPresent(attributeTransmitterDistributeProcessor::postRestMappings);
    }
}
