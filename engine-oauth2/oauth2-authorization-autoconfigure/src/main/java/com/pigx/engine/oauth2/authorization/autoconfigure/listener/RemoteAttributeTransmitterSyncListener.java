package com.pigx.engine.oauth2.authorization.autoconfigure.listener;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.core.identity.domain.AttributeTransmitter;
import com.pigx.engine.oauth2.authorization.autoconfigure.bus.RemoteAttributeTransmitterSyncEvent;
import com.pigx.engine.oauth2.authorization.processor.SecurityAttributeAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationListener;

import java.util.Optional;


public class RemoteAttributeTransmitterSyncListener implements ApplicationListener<RemoteAttributeTransmitterSyncEvent> {

    private static final Logger log = LoggerFactory.getLogger(RemoteAttributeTransmitterSyncListener.class);

    private final SecurityAttributeAnalyzer securityAttributeAnalyzer;
    private final ServiceMatcher serviceMatcher;

    public RemoteAttributeTransmitterSyncListener(SecurityAttributeAnalyzer securityAttributeAnalyzer, ServiceMatcher serviceMatcher) {
        this.securityAttributeAnalyzer = securityAttributeAnalyzer;
        this.serviceMatcher = serviceMatcher;
    }

    @Override
    public void onApplicationEvent(RemoteAttributeTransmitterSyncEvent event) {

        if (!serviceMatcher.isFromSelf(event)) {
            log.info("[PIGXD] |- Remote attribute transmitter sync listener, response service [{}] event!", event.getOriginService());

            String data = event.getData();

            log.debug("[PIGXD] |- Got attribute transmitter from service [{}], current [{}] start to process security attributes.", event.getOriginService(), event.getDestinationService());

            Optional.ofNullable(data)
                    .flatMap(value -> Optional.ofNullable(Jackson2Utils.toList(value, AttributeTransmitter.class)))
                    .ifPresent(securityAttributeAnalyzer::processAttributeTransmitters);
        }
    }
}
