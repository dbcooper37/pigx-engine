package com.pigx.engine.oauth2.authorization.autoconfigure.processor;

import com.pigx.engine.core.foundation.context.ServiceContextHolder;
import com.pigx.engine.core.foundation.founction.ListConverter;
import com.pigx.engine.core.identity.domain.AttributeTransmitter;
import com.pigx.engine.logic.upms.converter.SysAttributeToAttributeTransmitterConverter;
import com.pigx.engine.logic.upms.converter.SysInterfacesToSysAttributesConverter;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.logic.upms.entity.security.SysInterface;
import com.pigx.engine.logic.upms.service.security.SysAttributeService;
import com.pigx.engine.logic.upms.service.security.SysInterfaceService;
import com.pigx.engine.message.core.definition.strategy.StrategyEventManager;
import com.pigx.engine.message.core.domain.RestMapping;
import com.pigx.engine.message.core.event.ApplicationReadinessEvent;
import com.pigx.engine.oauth2.authorization.autoconfigure.bus.RemoteAttributeTransmitterSyncEvent;
import com.pigx.engine.oauth2.authorization.processor.SecurityAttributeAnalyzer;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/* JADX WARN: Classes with same name are omitted:
  
 */
@Component
/* loaded from: oauth2-authorization-autoconfigure-3.5.7.0.jar:cn/herodotus/engine/oauth2/authorization/autoconfigure/processor/AttributeTransmitterDistributeProcessor.class */
public class AttributeTransmitterDistributeProcessor implements StrategyEventManager<List<AttributeTransmitter>> {
    private static final Logger log = LoggerFactory.getLogger(AttributeTransmitterDistributeProcessor.class);
    private final ListConverter<SysInterface, SysAttribute> toSysAttributes = new SysInterfacesToSysAttributesConverter();
    private final ListConverter<SysAttribute, AttributeTransmitter> toTransmitters = new SysAttributeToAttributeTransmitterConverter();
    private final SysAttributeService sysAttributeService;
    private final SysInterfaceService sysInterfaceService;
    private final SecurityAttributeAnalyzer securityAttributeAnalyzer;

    public AttributeTransmitterDistributeProcessor(SysAttributeService sysAttributeService, SysInterfaceService sysInterfaceService, SecurityAttributeAnalyzer securityAttributeAnalyzer) {
        this.sysAttributeService = sysAttributeService;
        this.sysInterfaceService = sysInterfaceService;
        this.securityAttributeAnalyzer = securityAttributeAnalyzer;
    }

    @Override // com.pigx.engine.message.core.definition.strategy.StrategyEventManager
    public void postLocalProcess(List<AttributeTransmitter> data) {
        this.securityAttributeAnalyzer.processAttributeTransmitters(data);
    }

    @Override // com.pigx.engine.message.core.definition.strategy.StrategyEventManager
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteAttributeTransmitterSyncEvent(data, originService, destinationService));
    }

    @Transactional(rollbackFor = {Exception.class})
    public void postRestMappings(List<RestMapping> restMappings) {
        List<SysInterface> storedInterfaces = this.sysInterfaceService.storeRequestMappings(restMappings);
        if (CollectionUtils.isNotEmpty(storedInterfaces)) {
            log.debug("[Herodotus] |- [R5] Request mapping store success, start to merge security metadata!");
            List<SysInterface> sysInterfaces = this.sysInterfaceService.findAllocatable();
            if (CollectionUtils.isNotEmpty(sysInterfaces)) {
                List<SysAttribute> elements = this.toSysAttributes.convert(sysInterfaces);
                if (CollectionUtils.isNotEmpty(this.sysAttributeService.saveAllAndFlush(elements))) {
                    log.debug("[Herodotus] |- Merge security attribute SUCCESS and FINISHED!");
                } else {
                    log.error("[Herodotus] |- Merge Security attribute failed!, Please Check!");
                }
            } else {
                log.debug("[Herodotus] |- No security attribute requires merge, SKIP!");
            }
            distributeServiceSecurityAttributes(storedInterfaces);
            if (!ServiceContextHolder.isDistributedArchitecture()) {
                publishEvent(new ApplicationReadinessEvent("Attribute Transmitter Distribute Success"));
            }
        }
    }

    private void distributeServiceSecurityAttributes(List<SysInterface> storedInterfaces) {
        storedInterfaces.stream().findAny().map((v0) -> {
            return v0.getServiceId();
        }).ifPresent(this::distributionToService);
    }

    public void distributionToService(String serviceId) {
        List<SysAttribute> sysAttributes = this.sysAttributeService.findAllByServiceId(serviceId);
        if (CollectionUtils.isNotEmpty(sysAttributes)) {
            List<AttributeTransmitter> attributeTransmitters = this.toTransmitters.convert(sysAttributes);
            log.debug("[Herodotus] |- [R6] Synchronization permissions to service [{}]", serviceId);
            postProcess(serviceId, attributeTransmitters);
        }
    }

    public void distributeChangedSecurityAttribute(SysAttribute sysAttribute) {
        AttributeTransmitter attributeTransmitter = this.toTransmitters.from(sysAttribute);
        postProcess(attributeTransmitter.getServiceId(), ImmutableList.of(attributeTransmitter));
    }
}
