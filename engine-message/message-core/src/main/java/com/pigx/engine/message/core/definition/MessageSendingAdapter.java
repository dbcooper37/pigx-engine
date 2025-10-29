package com.pigx.engine.message.core.definition;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.message.core.domain.Message;
import org.springframework.context.ApplicationListener;


public interface MessageSendingAdapter<D extends Message, E extends AbstractApplicationEvent<D>> extends ApplicationListener<E> {

}
