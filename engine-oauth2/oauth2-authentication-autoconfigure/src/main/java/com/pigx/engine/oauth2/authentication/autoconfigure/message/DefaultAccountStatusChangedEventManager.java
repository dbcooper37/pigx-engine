package com.pigx.engine.oauth2.authentication.autoconfigure.message;

import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.message.core.definition.strategy.AccountStatusChangedEventManager;
import com.pigx.engine.message.core.domain.AccountStatus;
import com.pigx.engine.message.core.event.AccountStatusChangedEvent;
import com.pigx.engine.oauth2.authorization.autoconfigure.bus.RemoteAccountStatusChangedEvent;


public class DefaultAccountStatusChangedEventManager implements AccountStatusChangedEventManager {

    @Override
    public String getDestinationServiceName() {
        return ServiceContextHolder.getUpmsServiceName();
    }

    @Override
    public void postLocalProcess(AccountStatus data) {
        publishEvent(new AccountStatusChangedEvent(data));
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteAccountStatusChangedEvent(data, originService, destinationService));
    }
}
