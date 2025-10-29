package com.pigx.engine.oauth2.authorization.autoconfigure.listener;

import com.pigx.engine.message.core.domain.RestMapping;
import com.pigx.engine.message.core.event.RestMappingGatherEvent;
import com.pigx.engine.oauth2.authorization.autoconfigure.processor.AttributeTransmitterDistributeProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class LocalRestMappingGatherListener implements ApplicationListener<RestMappingGatherEvent> {

    private static final Logger log = LoggerFactory.getLogger(LocalRestMappingGatherListener.class);

    private final AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor;

    public LocalRestMappingGatherListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
        this.attributeTransmitterDistributeProcessor = attributeTransmitterDistributeProcessor;
    }

    @Override
    public void onApplicationEvent(RestMappingGatherEvent event) {

        log.info("[PIGXD] |- Rest mapping gather LOCAL listener, response event!");

        List<RestMapping> restMappings = event.getData();
        if (CollectionUtils.isNotEmpty(restMappings)) {
            log.debug("[PIGXD] |- [R4] Request mapping process BEGIN!");
            attributeTransmitterDistributeProcessor.postRestMappings(restMappings);
        }
    }
}
