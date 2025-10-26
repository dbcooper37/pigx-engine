package com.pigx.engine.oauth2.authorization.autoconfigure.listener;

import com.pigx.engine.core.definition.utils.Jackson2Utils;
import com.pigx.engine.core.identity.domain.AttributeTransmitter;
import com.pigx.engine.oauth2.authorization.autoconfigure.bus.RemoteAttributeTransmitterSyncEvent;
import com.pigx.engine.oauth2.authorization.processor.SecurityAttributeAnalyzer;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationListener;

/* JADX WARN: Classes with same name are omitted:
  
 */
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/listener/RemoteAttributeTransmitterSyncListener.class */
public class RemoteAttributeTransmitterSyncListener implements ApplicationListener<RemoteAttributeTransmitterSyncEvent> {
    private static final Logger log = LoggerFactory.getLogger(RemoteAttributeTransmitterSyncListener.class);
    private final SecurityAttributeAnalyzer securityAttributeAnalyzer;
    private final ServiceMatcher serviceMatcher;

    public RemoteAttributeTransmitterSyncListener(SecurityAttributeAnalyzer securityAttributeAnalyzer, ServiceMatcher serviceMatcher) {
        this.securityAttributeAnalyzer = securityAttributeAnalyzer;
        this.serviceMatcher = serviceMatcher;
    }

    public void onApplicationEvent(RemoteAttributeTransmitterSyncEvent event) {
        if (!this.serviceMatcher.isFromSelf(event)) {
            log.info("[Herodotus] |- Remote attribute transmitter sync listener, response service [{}] event!", event.getOriginService());
            String data = event.getData();
            log.debug("[Herodotus] |- Got attribute transmitter from service [{}], current [{}] start to process security attributes.", event.getOriginService(), event.getDestinationService());
            Optional optionalFlatMap = Optional.ofNullable(data).flatMap(value -> {
                return Optional.ofNullable(Jackson2Utils.toList(value, AttributeTransmitter.class));
            });
            SecurityAttributeAnalyzer securityAttributeAnalyzer = this.securityAttributeAnalyzer;
            Objects.requireNonNull(securityAttributeAnalyzer);
            optionalFlatMap.ifPresent(securityAttributeAnalyzer::processAttributeTransmitters);
        }
    }
}
