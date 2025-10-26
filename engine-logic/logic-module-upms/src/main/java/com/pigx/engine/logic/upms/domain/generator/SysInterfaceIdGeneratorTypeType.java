package com.pigx.engine.logic.upms.domain.generator;

import com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType;
import com.pigx.engine.logic.upms.entity.security.SysInterface;
import java.lang.reflect.Member;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

/* loaded from: logic-module-upms-3.5.7.0.jar:cn/herodotus/engine/logic/upms/domain/generator/SysInterfaceIdGeneratorTypeType.class */
public class SysInterfaceIdGeneratorTypeType extends AbstractIdGeneratorType {
    public SysInterfaceIdGeneratorTypeType(SysInterfaceIdGenerator config, Member member, CustomIdGeneratorCreationContext context) {
        super(member);
    }

    @Override // com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType
    public Object generate(SharedSessionContractImplementor session, Object object) {
        SysInterface sysInterface = (SysInterface) object;
        if (StringUtils.isEmpty(sysInterface.getInterfaceId())) {
            return super.generate(session, object);
        }
        return sysInterface.getInterfaceId();
    }
}
