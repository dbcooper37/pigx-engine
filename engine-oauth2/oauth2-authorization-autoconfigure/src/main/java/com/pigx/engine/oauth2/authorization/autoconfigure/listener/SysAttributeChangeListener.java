package com.pigx.engine.oauth2.authorization.autoconfigure.listener;

import com.pigx.engine.logic.upms.domain.event.SysAttributeChangeEvent;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.oauth2.authorization.autoconfigure.processor.AttributeTransmitterDistributeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class SysAttributeChangeListener implements ApplicationListener<SysAttributeChangeEvent> {

    private static final Logger log = LoggerFactory.getLogger(SysAttributeChangeListener.class);

    private final AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor;

    public SysAttributeChangeListener(AttributeTransmitterDistributeProcessor attributeTransmitterDistributeProcessor) {
        this.attributeTransmitterDistributeProcessor = attributeTransmitterDistributeProcessor;
    }

    @Override
    public void onApplicationEvent(SysAttributeChangeEvent event) {

        log.debug("[PIGXD] |- SysAttribute Change Listener, response event!");

        SysAttribute sysAttribute = event.getData();

        log.debug("[PIGXD] |- Got SysAttribute, start to process SysAttribute change.");

        Optional.ofNullable(sysAttribute)
                .ifPresent(attributeTransmitterDistributeProcessor::distributeChangedSecurityAttribute);
    }
}
