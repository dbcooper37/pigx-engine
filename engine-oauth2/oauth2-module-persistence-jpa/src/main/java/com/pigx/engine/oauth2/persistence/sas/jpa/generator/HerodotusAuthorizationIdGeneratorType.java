package com.pigx.engine.oauth2.persistence.sas.jpa.generator;

import com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorization;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

import java.lang.reflect.Member;


public class HerodotusAuthorizationIdGeneratorType extends AbstractIdGeneratorType {

    public HerodotusAuthorizationIdGeneratorType(HerodotusAuthorizationIdGenerator config, Member member, CustomIdGeneratorCreationContext context) {
        super(member);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {

        HerodotusAuthorization HerodotusAuthorization = (HerodotusAuthorization) object;

        if (StringUtils.isEmpty(HerodotusAuthorization.getId())) {
            return super.generate(session, object);
        } else {
            return HerodotusAuthorization.getId();
        }
    }
}
