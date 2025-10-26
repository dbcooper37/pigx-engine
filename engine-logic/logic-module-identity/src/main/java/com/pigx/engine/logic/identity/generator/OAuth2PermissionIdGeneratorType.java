package com.pigx.engine.logic.identity.generator;

import com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType;
import com.pigx.engine.logic.identity.entity.OAuth2Permission;
import java.lang.reflect.Member;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;

/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/generator/OAuth2PermissionIdGeneratorType.class */
public class OAuth2PermissionIdGeneratorType extends AbstractIdGeneratorType {
    public OAuth2PermissionIdGeneratorType(OAuth2PermissionIdGenerator config, Member member, CustomIdGeneratorCreationContext context) {
        super(member);
    }

    @Override // com.pigx.engine.data.hibernate.generator.AbstractIdGeneratorType
    public Object generate(SharedSessionContractImplementor session, Object object) {
        OAuth2Permission permission = (OAuth2Permission) object;
        if (StringUtils.isEmpty(permission.getPermissionId())) {
            return super.generate(session, object);
        }
        return permission.getPermissionId();
    }
}
