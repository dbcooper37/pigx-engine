package com.pigx.engine.message.core.definition;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.Message;
import org.springframework.context.ApplicationListener;

/* loaded from: message-core-3.5.7.0.jar:cn/herodotus/engine/message/core/definition/MessageSendingAdapter.class */
public interface MessageSendingAdapter<D extends Message, E extends AbstractApplicationEvent<D>> extends ApplicationListener<E> {
}
