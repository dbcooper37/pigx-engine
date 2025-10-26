package com.pigx.engine.logic.upms.converter;

import com.pigx.engine.core.foundation.founction.ListConverter;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.logic.upms.entity.security.SysInterface;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/converter/SysInterfacesToSysAttributesConverter.class */
public class SysInterfacesToSysAttributesConverter implements ListConverter<SysInterface, SysAttribute> {
    @Override // com.pigx.engine.core.foundation.founction.ListConverter
    public SysAttribute from(SysInterface source) {
        SysAttribute target = new SysAttribute();
        target.setAttributeId(source.getInterfaceId());
        target.setAttributeCode(source.getInterfaceCode());
        target.setRequestMethod(source.getRequestMethod());
        target.setServiceId(source.getServiceId());
        target.setClassName(source.getClassName());
        target.setMethodName(source.getMethodName());
        target.setUrl(source.getUrl());
        target.setStatus(source.getStatus());
        target.setReserved(source.getReserved());
        target.setDescription(source.getDescription());
        target.setReversion(source.getReversion());
        target.setCreateTime(source.getCreateTime());
        target.setUpdateTime(source.getUpdateTime());
        target.setRanking(source.getRanking());
        return target;
    }
}
