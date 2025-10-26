package com.pigx.engine.logic.identity.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.identity.entity.OAuth2Application;

/* loaded from: logic-module-identity-3.5.7.0.jar:cn/herodotus/engine/logic/identity/repository/OAuth2ApplicationRepository.class */
public interface OAuth2ApplicationRepository extends BaseJpaRepository<OAuth2Application, String> {
    OAuth2Application findByClientId(String clientId);
}
