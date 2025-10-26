package com.pigx.engine.oauth2.persistence.sas.jpa.repository;

import com.pigx.engine.data.core.jpa.repository.BaseJpaRepository;
import com.pigx.engine.oauth2.persistence.sas.jpa.entity.HerodotusRegisteredClient;
import jakarta.persistence.QueryHint;
import java.util.Optional;
import org.springframework.data.jpa.repository.QueryHints;

/* loaded from: oauth2-module-persistence-jpa-3.5.7.0.jar:cn/herodotus/engine/oauth2/persistence/sas/jpa/repository/HerodotusRegisteredClientRepository.class */
public interface HerodotusRegisteredClientRepository extends BaseJpaRepository<HerodotusRegisteredClient, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.cacheable", value = "true")})
    Optional<HerodotusRegisteredClient> findByClientId(String clientId);
}
