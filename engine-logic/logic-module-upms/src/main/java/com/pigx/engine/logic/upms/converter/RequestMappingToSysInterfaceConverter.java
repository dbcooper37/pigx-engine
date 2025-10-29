package com.pigx.engine.logic.upms.converter;

import com.pigx.engine.logic.upms.entity.security.SysInterface;
import com.pigx.engine.message.core.domain.RestMapping;
import org.springframework.core.convert.converter.Converter;


public class RequestMappingToSysInterfaceConverter implements Converter<RestMapping, SysInterface> {

    @Override
    public SysInterface convert(RestMapping restMapping) {
        SysInterface sysInterface = new SysInterface();
        sysInterface.setInterfaceId(restMapping.getMappingId());
        sysInterface.setInterfaceCode(restMapping.getMappingCode());
        sysInterface.setRequestMethod(restMapping.getRequestMethod());
        sysInterface.setServiceId(restMapping.getServiceId());
        sysInterface.setClassName(restMapping.getClassName());
        sysInterface.setMethodName(restMapping.getMethodName());
        sysInterface.setUrl(restMapping.getUrl());
        sysInterface.setDescription(restMapping.getDescription());
        return sysInterface;
    }
}
