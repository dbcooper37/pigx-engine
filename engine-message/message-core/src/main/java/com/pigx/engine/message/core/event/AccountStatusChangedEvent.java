package com.pigx.engine.message.core.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.AccountStatus;

import java.time.Clock;


public class AccountStatusChangedEvent extends AbstractApplicationEvent<AccountStatus> {

    public AccountStatusChangedEvent(AccountStatus data) {
        super(data);
    }

    public AccountStatusChangedEvent(AccountStatus data, Clock clock) {
        super(data, clock);
    }
}
