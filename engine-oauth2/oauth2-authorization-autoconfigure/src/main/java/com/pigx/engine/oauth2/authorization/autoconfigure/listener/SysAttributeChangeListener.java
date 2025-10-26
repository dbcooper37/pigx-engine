package com.pigx.engine.oauth2.authorization.autoconfigure.listener;

import com.pigx.engine.logic.upms.domain.event.SysAttributeChangeEvent;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.oauth2.authorization.autoconfigure.processor.AttributeTransmitterDistributeProcessor;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/* JADX WARN: Classes with same name are omitted:
  
 */
@Component
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/listener/SysAttributeChangeListener.class */
public class SysAttributeChangeListener implements ApplicationListener<SysAttributeChangeEvent> {
    private static final Logger log = LoggerFactory.getLogger(SysAttributeChangeListener.class);
    private final AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor;

    public SysAttributeChangeListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
        this.attributeTransmitterDistributeProcessor = attributeTransmitterDistributeProcessor;
    }

    public void onApplicationEvent(SysAttributeChangeEvent event) {
        log.debug("[Herodotus] |- SysAttribute Change Listener, response event!");
        SysAttribute sysAttribute = event.getData();
        log.debug("[Herodotus] |- Got SysAttribute, start to process SysAttribute change.");
        Optional optionalOfNullable = Optional.ofNullable(sysAttribute);
        AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor = this.attributeTransmitterDistributeProcessor;
        Objects.requireNonNull(attributeTransmitterDistributeProcessor);
        optionalOfNullable.ifPresent(attributeTransmitterDistributeProcessor::distributeChangedSecurityAttribute);
    }
}
