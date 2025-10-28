package com.pigx.engine.rest.servlet.upms.event;

import java.util.Map;
import org.springframework.context.ApplicationEvent;

/* loaded from: rest-module-servlet-upms-3.5.7.0.jar:cn/herodotus/engine/rest/servlet/upms/event/AutomaticSignInEvent.class */
public class AutomaticSignInEvent extends ApplicationEvent {
    private final Map<String, Object> callbackParams;

    public AutomaticSignInEvent(Map<String, Object> callbackParams) {
        super(callbackParams);
        this.callbackParams = callbackParams;
    }

    public Map<String, Object> getCallbackParams() {
        return this.callbackParams;
    }
}
