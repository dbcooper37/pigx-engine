package com.pigx.engine.message.core.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.DialogueMessage;
import java.time.Clock;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/event/SendDialogueMessageEvent.class */
public class SendDialogueMessageEvent extends AbstractApplicationEvent<DialogueMessage> {
    public SendDialogueMessageEvent(DialogueMessage data) {
        super(data);
    }

    public SendDialogueMessageEvent(DialogueMessage data, Clock clock) {
        super(data, clock);
    }
}
