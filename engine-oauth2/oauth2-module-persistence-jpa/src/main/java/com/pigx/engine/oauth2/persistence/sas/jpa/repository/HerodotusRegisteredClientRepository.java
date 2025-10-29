package com.pigx.engine.oauth2.persistence.sas.jpa.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusRegisteredClient;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;


public interface HerodotusRegisteredClientRepository extends BaseJpaRepository<HerodotusRegisteredClient, String> {

    /**
     * 根据 ClientId 查询 RegisteredClient
     *
     * @param clientId OAuth2 客户端ID
     * @return OAuth2 客户端配置
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<HerodotusRegisteredClient> findByClientId(String clientId);
}
