package com.pigx.engine.logic.upms.domain.generator;

import com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType;
import com.pigx.engine.logic.upms.entity.security.SysAttribute;
import java.lang.reflect.Member;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/domain/generator/SysAttributeIdGeneratorType.class */
public class SysAttributeIdGeneratorType extends AbstractIdGeneratorType {
    public SysAttributeIdGeneratorType(SysAttributeIdGenerator config, Member member, CustomIdGeneratorCreationContext context) {
        super(member);
    }

    @Override // com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType
    public Object generate(SharedSessionContractImplementor session, Object object) {
        SysAttribute sysAttribute = (SysAttribute) object;
        if (StringUtils.isEmpty(sysAttribute.getAttributeId())) {
            return super.generate(session, object);
        }
        return sysAttribute.getAttributeId();
    }
}
