package com.pigx.engine.logic.upms.domain.listener;

import com.pigx.engine.core.foundation.context.AbstractApplicationContextAware;
import com.pigx.engine.logic.upms.domain.event.SysAttributeChangeEvent;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import jakarta.persistence.PostUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/domain/listener/SysAttributeEntityListener.class */
public class SysAttributeEntityListener extends AbstractApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(SysAttributeEntityListener.class);

    @PostUpdate
    protected void postUpdate(SysAttribute entity) {
        log.debug("[Herodotus] |- [1] SysAttribute entity @PostUpdate activated, value is : [{}]. Trigger SysAttribute change event.", entity.toString());
        publishEvent(new SysAttributeChangeEvent(entity));
    }
}
