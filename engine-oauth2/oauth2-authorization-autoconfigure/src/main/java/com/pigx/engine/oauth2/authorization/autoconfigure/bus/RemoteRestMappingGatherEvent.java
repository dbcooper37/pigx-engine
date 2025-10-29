package com.pigx.engine.oauth2.authorization.autoconfigure.bus;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;


public class RemoteRestMappingGatherEvent extends RemoteApplicationEvent {

    private String data;

    public RemoteRestMappingGatherEvent() {
        super();
    }

    public RemoteRestMappingGatherEvent(String data, String originService, String destinationService) {
        super(data, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
