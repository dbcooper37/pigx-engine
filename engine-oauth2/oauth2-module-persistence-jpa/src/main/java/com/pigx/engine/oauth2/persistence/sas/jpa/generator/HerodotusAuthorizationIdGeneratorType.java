package com.pigx.engine.oauth2.persistence.sas.jpa.generator;

import com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorization;
import java.lang.reflect.Member;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/generator/HerodotusAuthorizationIdGeneratorType.class */
public class HerodotusAuthorizationIdGeneratorType extends AbstractIdGeneratorType {
    public HerodotusAuthorizationIdGeneratorType(HerodotusAuthorizationIdGenerator config, Member member, CustomIdGeneratorCreationContext context) {
        super(member);
    }

    @Override // com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType
    public Object generate(SharedSessionContractImplementor session, Object object) {
        HerodotusAuthorization HerodotusAuthorization = (HerodotusAuthorization) object;
        if (StringUtils.isEmpty(HerodotusAuthorization.getId())) {
            return super.generate(session, object);
        }
        return HerodotusAuthorization.getId();
    }
}
