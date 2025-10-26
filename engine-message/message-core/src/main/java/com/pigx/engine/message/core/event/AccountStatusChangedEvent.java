package com.pigx.engine.message.core.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.AccountStatus;
import java.time.Clock;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/event/AccountStatusChangedEvent.class */
public class AccountStatusChangedEvent extends AbstractApplicationEvent<AccountStatus> {
    public AccountStatusChangedEvent(AccountStatus data) {
        super(data);
    }

    public AccountStatusChangedEvent(AccountStatus data, Clock clock) {
        super(data, clock);
    }
}
