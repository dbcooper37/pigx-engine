package com.pigx.engine.oauth2.persistence.sas.jpa.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorization;
import jakarta.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/repository/HerodotusAuthorizationRepository.class */
public interface HerodotusAuthorizationRepository extends BaseJpaRepository<HerodotusAuthorization, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<HerodotusAuthorization> findByState(String state);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<HerodotusAuthorization> findByAuthorizationCodeValue(String authorizationCode);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<HerodotusAuthorization> findByAccessTokenValue(String accessToken);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<HerodotusAuthorization> findByRefreshTokenValue(String refreshToken);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<HerodotusAuthorization> findByOidcIdTokenValue(String idToken);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<HerodotusAuthorization> findByUserCodeValue(String userCode);

    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<HerodotusAuthorization> findByDeviceCodeValue(String deviceCode);

    List<HerodotusAuthorization> findAllByRegisteredClientIdAndPrincipalNameAndAccessTokenExpiresAtAfter(String registeredClientId, String principalName, LocalDateTime localDateTime);

    @Modifying
    @Transactional
    void deleteByRefreshTokenExpiresAtBefore(LocalDateTime localDateTime);
}
