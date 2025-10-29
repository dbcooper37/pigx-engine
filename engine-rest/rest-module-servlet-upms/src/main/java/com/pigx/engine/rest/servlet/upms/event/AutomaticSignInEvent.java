package com.pigx.engine.rest.servlet.upms.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;


public class AutomaticSignInEvent extends ApplicationEvent {

    private final Map<String, Object> callbackParams;

    public AutomaticSignInEvent(Map<String, Object> callbackParams) {
        super(callbackParams);
        this.callbackParams = callbackParams;
    }

    public Map<String, Object> getCallbackParams() {
        return callbackParams;
    }
}
