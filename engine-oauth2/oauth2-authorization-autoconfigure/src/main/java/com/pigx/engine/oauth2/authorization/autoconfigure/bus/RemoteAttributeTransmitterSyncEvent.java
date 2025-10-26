package com.pigx.engine.oauth2.authorization.autoconfigure.bus;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/* JADX WARN: Classes with same name are omitted:
  
 */
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/bus/RemoteAttributeTransmitterSyncEvent.class */
public class RemoteAttributeTransmitterSyncEvent extends RemoteApplicationEvent {
    private String data;

    public RemoteAttributeTransmitterSyncEvent() {
    }

    public RemoteAttributeTransmitterSyncEvent(String data, String originService, String destinationService) {
        super(data, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.data = data;
    }

    public String getData() {
        return this.data;
    }
}
