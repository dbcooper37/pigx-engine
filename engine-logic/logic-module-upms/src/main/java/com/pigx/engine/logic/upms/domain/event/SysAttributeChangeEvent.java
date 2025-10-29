package com.pigx.engine.logic.upms.domain.event;

import com.pigx.engine.core.foundation.context.AbstractApplicationEvent;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;

import java.time.Clock;


public class SysAttributeChangeEvent extends AbstractApplicationEvent<SysAttribute> {

    public SysAttributeChangeEvent(SysAttribute data) {
        super(data);
    }

    public SysAttributeChangeEvent(SysAttribute data, Clock clock) {
        super(data, clock);
    }
}
