package com.pigx.engine.logic.upms.converter;

import com.pigx.engine.core.definition.constant.SymbolConstants;
import com.pigx.engine.core.foundation.founction.ListConverter;
import com.pigx.engine.core.identity.domain.AttributeTransmitter;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import com.pigx.engine.logic.upms.entity.security.SysPermission;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/converter/SysAttributeToAttributeTransmitterConverter.class */
public class SysAttributeToAttributeTransmitterConverter implements ListConverter<SysAttribute, AttributeTransmitter> {
    @Override // com.pigx.engine.core.foundation.founction.ListConverter
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
            List<String> codes = sysAuthorities.stream().map((v0) -> {
                return v0.getPermissionCode();
            }).toList();
            return StringUtils.collectionToCommaDelimitedString(codes);
        }
        return SymbolConstants.BLANK;
    }
}
