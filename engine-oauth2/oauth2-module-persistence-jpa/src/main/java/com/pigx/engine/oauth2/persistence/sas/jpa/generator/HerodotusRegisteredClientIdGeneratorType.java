package com.pigx.engine.oauth2.persistence.sas.jpa.generator;

import com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusRegisteredClient;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

import java.lang.reflect.Member;


public class HerodotusRegisteredClientIdGeneratorType extends AbstractIdGeneratorType {

    public HerodotusRegisteredClientIdGeneratorType(HerodotusRegisteredClientIdGenerator config, Member member, CustomIdGeneratorCreationContext context) {
        super(member);
    }

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {

        HerodotusRegisteredClient herodotusRegisteredClient = (HerodotusRegisteredClient) object;

        if (StringUtils.isEmpty(herodotusRegisteredClient.getId())) {
            return super.generate(session, object);
        } else {
            return herodotusRegisteredClient.getId();
        }
    }
}
