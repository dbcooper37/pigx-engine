package com.pigx.engine.oauth2.persistence.sas.jpa.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorizationConsent;
import com.pigx.engine.oauth2.persistence.sas.jpa.generator.HerodotusAuthorizationConsentId;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;


public interface HerodotusAuthorizationConsentRepository extends BaseJpaRepository<HerodotusAuthorizationConsent, HerodotusAuthorizationConsentId> {

    /**
     * 根据 client id 和 principalName 查询 OAuth2 确认信息
     *
     * @param registeredClientId 注册OAuth2客户端ID
     * @param principalName      用户名
     * @return OAuth2 认证确认信息 {@link HerodotusAuthorizationConsent}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<HerodotusAuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    /**
     * 根据 client id 和 principalName 删除 OAuth2 确认信息
     *
     * @param registeredClientId 注册OAuth2客户端ID
     * @param principalName      用户名
     */
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
