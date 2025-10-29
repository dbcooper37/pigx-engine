package com.pigx.engine.oauth2.authorization.autoconfigure.processor;

import com.google.common.collect.ImmutableList;
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
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class AttributeTransmitterDistributeProcessor implements StrategyEventManager<List<AttributeTransmitter>> {

    private static final Logger log = LoggerFactory.getLogger(AttributeTransmitterDistributeProcessor.class);

    private final ListConverter<SysInterface, SysAttribute> toSysAttributes;
    private final ListConverter<SysAttribute, AttributeTransmitter> toTransmitters;

    private final SysAttributeService sysAttributeService;
    private final SysInterfaceService sysInterfaceService;
    private final SecurityAttributeAnalyzer securityAttributeAnalyzer;

    public AttributeTransmitterDistributeProcessor(SysAttributeService sysAttributeService, SysInterfaceService sysInterfaceService, SecurityAttributeAnalyzer securityAttributeAnalyzer) {
        this.sysAttributeService = sysAttributeService;
        this.sysInterfaceService = sysInterfaceService;
        this.securityAttributeAnalyzer = securityAttributeAnalyzer;
        this.toSysAttributes = new SysInterfacesToSysAttributesConverter();
        this.toTransmitters = new SysAttributeToAttributeTransmitterConverter();
    }

    /**
     * UPMS 服务既要处理各个服务权限数据的分发，也要处理自身服务权限数据
     *
     * @param data 事件携带数据
     */
    @Override
    public void postLocalProcess(List<AttributeTransmitter> data) {
        securityAttributeAnalyzer.processAttributeTransmitters(data);
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteAttributeTransmitterSyncEvent(data, originService, destinationService));
    }

    /**
     * 将SysAuthority表中存在，但是SysSecurityAttribute中不存在的数据同步至SysSecurityAttribute，保证两侧数据一致
     */
    @Transactional(rollbackFor = Exception.class)
    public void postRestMappings(List<RestMapping> restMappings) {

        // 将各个服务发送回来的 requestMappings 存储到 SysInterface 中
        List<SysInterface> storedInterfaces = sysInterfaceService.storeRequestMappings(restMappings);

        if (CollectionUtils.isNotEmpty(storedInterfaces)) {
            log.debug("[PIGXD] |- [R5] Request mapping store success, start to merge security metadata!");

            // 查询将新增的 SysInterface，将其转存到 SysAttribute 中
            List<SysInterface> sysInterfaces = sysInterfaceService.findAllocatable();
            if (CollectionUtils.isNotEmpty(sysInterfaces)) {
                List<SysAttribute> elements = toSysAttributes.convert(sysInterfaces);
                List<SysAttribute> result = sysAttributeService.saveAllAndFlush(elements);
                if (CollectionUtils.isNotEmpty(result)) {
                    log.debug("[PIGXD] |- Merge security attribute SUCCESS and FINISHED!");
                } else {
                    log.error("[PIGXD] |- Merge Security attribute failed!, Please Check!");
                }
            } else {
                log.debug("[PIGXD] |- No security attribute requires merge, SKIP!");
            }

            // 执行权限数据分发
            distributeServiceSecurityAttributes(storedInterfaces);

            if (!ServiceContextHolder.isDistributedArchitecture()) {
                publishEvent(new ApplicationReadinessEvent("Attribute Transmitter Distribute Success"));
            }
//
//            List<SysAttribute> sysAttributes = sysAttributeService.findAll();
//            this.postGroupProcess(sysAttributes);
        }
    }

    private void distributeServiceSecurityAttributes(List<SysInterface> storedInterfaces) {
        // 每次处理都是只针对一个服务，所以该组数据 serviceId 肯定都相同
        storedInterfaces.stream()
                .findAny()
                .map(SysInterface::getServiceId)
                .ifPresent(this::distributionToService);
    }

    public void distributionToService(String serviceId) {
        List<SysAttribute> sysAttributes = sysAttributeService.findAllByServiceId(serviceId);
        if (CollectionUtils.isNotEmpty(sysAttributes)) {
            List<AttributeTransmitter> attributeTransmitters = toTransmitters.convert(sysAttributes);
            log.debug("[PIGXD] |- [R6] Synchronization permissions to service [{}]", serviceId);
            this.postProcess(serviceId, attributeTransmitters);
        }
    }

    public void distributeChangedSecurityAttribute(SysAttribute sysAttribute) {
        AttributeTransmitter attributeTransmitter = toTransmitters.from(sysAttribute);
        postProcess(attributeTransmitter.getServiceId(), ImmutableList.of(attributeTransmitter));
    }
}
