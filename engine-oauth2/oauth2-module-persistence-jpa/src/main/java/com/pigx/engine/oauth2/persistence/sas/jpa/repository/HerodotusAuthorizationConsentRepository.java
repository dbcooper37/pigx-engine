package com.pigx.engine.oauth2.persistence.sas.jpa.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusAuthorizationConsent;
import com.pigx.engine.oauth2.persistence.sas.jpa.generator.HerodotusAuthorizationConsentId;
import jakarta.persistence.QueryHint;
import java.util.Optional;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/repository/HerodotusAuthorizationConsentRepository.class */
public interface HerodotusAuthorizationConsentRepository extends BaseJpaRepository<HerodotusAuthorizationConsent, HerodotusAuthorizationConsentId> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<HerodotusAuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
