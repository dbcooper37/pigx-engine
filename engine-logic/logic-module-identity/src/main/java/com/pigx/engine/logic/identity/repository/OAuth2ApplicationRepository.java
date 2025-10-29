package com.pigx.engine.logic.identity.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.logic.identity.entity.OAuth2Application;


public interface OAuth2ApplicationRepository extends BaseJpaRepository<OAuth2Application, String> {

    /**
     * 根据 Client ID 查询 OAuth2Application
     *
     * @param clientId OAuth2Application 中的 clientId
     * @return {@link OAuth2Application}
     */
    OAuth2Application findByClientId(String clientId);
}
