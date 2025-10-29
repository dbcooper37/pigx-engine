package com.pigx.engine.logic.upms.domain.generator;

import com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType;
import com.pigx.engine.logic.upms.entity.security.SysInterface;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

import java.lang.reflect.Member;


public class SysInterfaceIdGeneratorTypeType extends AbstractIdGeneratorType {

    public SysInterfaceIdGeneratorTypeType(SysInterfaceIdGenerator config, Member member, CustomIdGeneratorCreationContext context) {
        super(member);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {

        SysInterface sysInterface = (SysInterface) object;

        if (StringUtils.isEmpty(sysInterface.getInterfaceId())) {
            return super.generate(session, object);
        } else {
            return sysInterface.getInterfaceId();
        }
    }
}
