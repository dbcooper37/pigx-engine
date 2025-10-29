package com.pigx.engine.logic.upms.converter;

import com.pigx.engine.core.foundation.founction.ListConverter;
import com.pigx.engine.core.identity.domain.AttributeTransmitter;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.logic.upms.entity.security.SysPermission;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;


public class SysAttributeToAttributeTransmitterConverter implements ListConverter<SysAttribute, AttributeTransmitter> {
    @Override
    public AttributeTransmitter from(SysAttribute source) {
        AttributeTransmitter target = new AttributeTransmitter();
        target.setAttributeId(source.getAttributeId());
        target.setAttributeCode(source.getAttributeCode());
        target.setWebExpression(source.getWebExpression());
        target.setPermissions(permissionToCommaDelimitedString(source.getPermissions()));
        target.setUrl(source.getUrl());
        target.setRequestMethod(source.getRequestMethod());
        target.setServiceId(source.getServiceId());
        target.setAttributeName(source.getDescription());
        return target;
    }

    private String permissionToCommaDelimitedString(Set<SysPermission> sysAuthorities) {
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            List<String> codes = sysAuthorities.stream().map(SysPermission::getPermissionCode).toList();
            return StringUtils.collectionToCommaDelimitedString(codes);
        } else {
            return "";
        }
    }
}
